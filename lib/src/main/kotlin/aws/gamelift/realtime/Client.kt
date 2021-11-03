package aws.gamelift.realtime

import java.lang.Math
import java.util.Timer
import kotlin.concurrent.schedule
import aws.gamelift.realtime.commands.*
import aws.gamelift.realtime.event.ErrorEventArgs
import aws.gamelift.realtime.network.ConnectionFactoryOptions
import aws.gamelift.realtime.network.ConnectionStats
import aws.gamelift.realtime.network.MessageEventArgs
import aws.gamelift.realtime.types.ConnectionStatus
import aws.gamelift.realtime.types.ConnectionType
import aws.gamelift.realtime.types.DeliveryIntent

import aws.gamelift.realtime.platform.EventArgs
import aws.gamelift.realtime.event.GroupMembershipEventArgs
import kotlin.math.pow

class ConnectionStatusHolder(var Status:ConnectionStatus = ConnectionStatus.READY)

class Client(private val clientConfiguration:ClientConfiguration,
             private var reliableConnection:Connection?,
			 /// <summary>Holds information about the connected session for the client.</summary>
             var session:ClientSession,
             private val connectionStatus:ConnectionStatusHolder,
             private val log:ClientLogger) : ClientEvents() {

    companion object {
        const val CLIENT_VERSION = "1.2.0"
        /// <summary>Maximum message size for reliable messages.</summary>
        /// <remarks>This limit applies to the payload field of messages.</remarks>
        const val MAX_RELIABLE_MESSAGE_BYTES = 4096

        /// <summary>Maximum message size for fast messages.</summary>
        /// <remarks>This limit applies to the payload field of messages.  Messages larger than
        ///          this limit, but smaller than maxReliableMessageBytes will fallback to the
        ///          reliable connection instead.</remarks>
        ///
        // NOTE: This value is to stay under the ethernet MTU of 1500 even after adding message
        //       header fields and the overhead of (future) DTLS.
        const val MAX_FAST_MESSAGE_BYTES = 1200

        // Parameters controlling retrying during UDP handshake
        private const val INITIAL_UDP_RETRY_MILLIS = 100
        private const val MAX_UDP_RETRY_MILLIS = 5 * 1000
    }

    /// <summary>Developer client version. Sent during connect.</summary>
    /// <remarks>This is only sent when you connect, can be used by sever scripts to separate incompatible clients.</remarks>
    var GameVersion: String? = null

    constructor(logger: ClientLogger)
        : this(ClientConfiguration.default(), logger)

    constructor(configuration:ClientConfiguration, logger: ClientLogger)
        : this(configuration, null, ConnectionStatusHolder(), logger)

    /*[Obsolete("This client is unsecured by default, prefer to use secure client instead. " +
              "See TLS Support on GameLift Documentation")]*/
    constructor(reliableConnection:Connection, connectionStatus:ConnectionStatusHolder, logger: ClientLogger) :
       this(ClientConfiguration.default(), reliableConnection, connectionStatus, logger)

    constructor(clientConfiguration:ClientConfiguration,
                  reliableConnection:Connection?,
                  connectionStatus:ConnectionStatusHolder,
                  logger:ClientLogger) :
        this(clientConfiguration,
               reliableConnection,
               ClientSession(/*"", -1, */null),
               connectionStatus,
               logger)

    /// <summary>
    /// False until client connects to GameLift Realtime. True while connected to any server.
    /// </summary>
    val connected:Boolean get() = (reliableConnection != null &&
                (connectionStatus.Status == ConnectionStatus.CONNECTED ||
                 connectionStatus.Status == ConnectionStatus.CONNECTED_SEND_FAST ||
                 connectionStatus.Status == ConnectionStatus.CONNECTED_SEND_AND_RECEIVE_FAST))

    /// <summary>
    /// A refined version of connected which is true only if the connection to the server is ready to accept operations like join, leave, etc.
    /// </summary>
    val connectedAndReady:Boolean
		get() = connected && session.loggedIn

    private lateinit var connectionFactoryOptions:ConnectionFactoryOptions
    private var fastConnection:Connection? = null    // currently udp
    private var listenUdpPort:Int = Constants.INVALID_PORT
    private lateinit var remoteEndpoint:String

    

    /// <summary>
    /// Connect to a Realtime server
    /// </summary>
    /// <param name="endpoint">The endpoint to connect to, for example the IpAddress returned for a game session</param>
    /// <param name="remoteTcpPort">The Realtime server's TCP port, for example the port of the game session</param>
    /// <param name="listenPort">The local client-side UDP listen port</param>
    /// <param name="token">The connection token to include</param>
    /// <returns></returns>
    fun connect(endpoint:String, remoteTcpPort:Int, listenPort:Int, token:ConnectionToken):ConnectionStatus
    {
        this.remoteEndpoint = endpoint
        this.listenUdpPort = listenPort
        val connFactOpts = ConnectionFactoryOptions(
            UdpListenPort = listenPort,
            HostName = endpoint,
            ClientConfiguration = clientConfiguration
        )
		this.connectionFactoryOptions = connFactOpts

        if (reliableConnection == null)
        {
            val wsConnection = clientConfiguration.reliableConnectionFactory.create(connFactOpts, log)
            connectionStatus.Status = ConnectionStatus.READY

            wsConnection.connectionOpen.add(::onConnectionOpen)
            wsConnection.connectionClose.add(::onConnectionClose)
            wsConnection.connectionError.add(::onConnectionError)
            wsConnection.messageReceived.add(::onMessageReceived)

            session = ClientSession(/*endpoint, remoteTcpPort, */token)
            reliableConnection = wsConnection
            wsConnection.initialize(endpoint, remoteTcpPort)
            connectionStatus.Status = ConnectionStatus.CONNECTING
            wsConnection.open()
        }
        return connectionStatus.Status
    }

    fun disconnect()
    {
        connectionStatus.Status = ConnectionStatus.DISCONNECTED_CLIENT_CALL
        reliableConnection?.close()
        reliableConnection = null

        fastConnection?.close()
        fastConnection = null
    }

    fun joinGroup(targetGroup:Int)
    {
        send(JoinGroup(session.connectedPeerId, targetGroup))

        session.joinGroup(targetGroup)
    }

    fun leaveGroup(targetGroup:Int)
    {
        send(LeaveGroup(session.connectedPeerId, targetGroup))

        session.leaveGroup(targetGroup)
    }

    fun requestGroupMembership(targetGroup:Int)
    {
        send(RequestGroupMembership(session.connectedPeerId, targetGroup))
    }

    /// <summary>
    /// Create a new Realtime Message using the passed OpCode and clients connected identity
    /// </summary>
    /// <param name="opCode">The opCode to set for the message</param>
    /// <returns>A Realtime message object</returns>
    fun newMessage(opCode:Int, intent:DeliveryIntent, targetPlayer:Int, payload:ByteArray):RTMessage
    {
        return RTMessage(opCode, session.connectedPeerId, deliveryIntent=intent, TargetPlayer=targetPlayer, Payload=payload)
    }

    /// <summary>
    /// Send a general RealtimeMessage via the server</summary>
    /// <remarks>
    /// If targetPlayer == Constants.PLAYER_ID_SERVER then message is sent to the server script's onMessage handler 
    /// If targetPlayer == <player id> then message is sent to the server script's onSendToPlayer handler.
    /// If targetGroup == <group id> hen message is sent to the server script's onSendToGroup handler.
    /// </remarks>
    ///
    /// <param name="message">The message to send</param>
    fun sendMessage(message:RTMessage)
    {
        send(message)
    }

    /// <summary>
    /// Sends a Realtime ClientEvent to the server (client to server message)
    /// </summary>
    /// <param name="opCode">The developer defined op code for the event</param>
    /// <param name="data">An optional payload to set on the event</param>
    fun sendEvent(opCode:Int, data:ByteArray? = null)
    {
        send(ClientEvent(opCode, session.connectedPeerId, data))
    }

    // Check if UDP communication is set up for both client and server
    val canSendFast:Boolean
		get() = connectionStatus.Status == ConnectionStatus.CONNECTED_SEND_FAST ||
	        connectionStatus.Status == ConnectionStatus.CONNECTED_SEND_AND_RECEIVE_FAST


    // Get UDP statistics for the client
    val getFastConnectionStats:ConnectionStats? get() = fastConnection?.getStats()

    // Get TCP statistics for the client
    fun getReliableConnectionStats():ConnectionStats?
    {
        return reliableConnection?.getStats()
    }

    // Reset the client's statistics sets
    fun resetStats()
    {
        fastConnection?.resetStats()
        reliableConnection?.resetStats()
    }

    private fun onConnectionOpen(sender:Any, args:EventArgs?)
    {
        connectionStatus.Status = ConnectionStatus.CONNECTED

        // Send login command
        val loginCommand = LoginCommand(session.token?.PlayerSessionId, session.token?.Payload, session.connectedPeerId)

        send(loginCommand)

        // Signal listeners
        onOpen()
    }

    private fun onConnectionClose(sender:Any, args:EventArgs?)
    {
        session.loggedIn = false
        // Closing for a reason other than client request
        if (connectionStatus.Status != ConnectionStatus.DISCONNECTED_CLIENT_CALL)
        {
            connectionStatus.Status = ConnectionStatus.DISCONNECTED
        }

        // Signal listeners
        onClose()
    }

    private fun onConnectionError(sender:Any, args:ErrorEventArgs?)
    {
        // Signal listeners
        onError(args?.exception)
    }

    private fun onMessageReceived(sender:Any, args:MessageEventArgs)
    {
        val result = args.Result

        when (result.OpCode)
        {
            Constants.LOGIN_RESPONSE_OP_CODE ->
                handleLoginResponse(result as LoginResult)

            Constants.GROUP_MEMBERSHIP_UPDATE_OP_CODE ->
                handleGroupMembershipUpdate(result as GroupMembershipUpdate)

            Constants.UDP_CONNECT_SERVER_ACK_OP_CODE ->
                handleUDPServerAck()

            Constants.VERIFY_IDENTITY_RESPONSE_OP_CODE->
                handleVerifyIdentityResponse(result as VerifyIdentityResult)

            else ->
                // Signal to developer
                onDataReceived(result.Sender, result.OpCode, result.Payload)
        }
    }

    private fun handleVerifyIdentityResponse(result:VerifyIdentityResult)
    {
        if (result.Success)
        {
            if (connectionStatus.Status == ConnectionStatus.CONNECTED)
            {
                connectionStatus.Status = ConnectionStatus.CONNECTED_SEND_FAST
            }
        }
    }

    private fun handleLoginResponse(result:LoginResult)
    {
        session.connectedPeerId = result.TargetPlayer
        session.loggedIn = result.Success

        if (result.Success)
        {
            // Once logged in over websocket we need to setup our UDP communication channel
            if (fastConnection == null)
            {
                connectionFactoryOptions.CaCert = result.CaCert
				val udpConnection = clientConfiguration.fastConnectionFactory.create(connectionFactoryOptions, log)
                fastConnection = udpConnection
                udpConnection.connectionOpen.add(::onConnectionOpen)
                udpConnection.connectionClose.add(::onConnectionClose)
                udpConnection.connectionError.add(::onConnectionError)
                udpConnection.messageReceived.add(::onMessageReceived)
                udpConnection.initialize(remoteEndpoint, result.UdpPort)
                udpConnection.open()
            }

            if (this.clientConfiguration.connectionType == ConnectionType.RT_OVER_WSS_DTLS_TLS12)
            {
                // DTLS handshake occurred with opening connection. Verify identity now.
                fastConnection?.send(VerifyIdentityCommand(session.connectedPeerId, result.ConnectToken, session.token?.Payload, session.connectedPeerId)
					.toPacket())
            }
            else
            {
                // Not DTLS, so do a regular UDP handshake.
                initiateUDPHandshake(0)
            }
        }
    }

    private fun handleGroupMembershipUpdate(groupMembershipUpdate:GroupMembershipUpdate)
    {
        val groupMembershipEventArgs = GroupMembershipEventArgs(groupMembershipUpdate.Sender, 
                groupMembershipUpdate.GroupId, groupMembershipUpdate.PlayerIds)

        session.updateGroupMembership(groupMembershipUpdate.GroupId,
                                      groupMembershipUpdate.PlayerIds)

        onGroupMembershipUpdated(groupMembershipEventArgs)
    }

    private fun handleUDPServerAck()
    {
        if (connectionStatus.Status == ConnectionStatus.CONNECTED)
        {
            connectionStatus.Status = ConnectionStatus.CONNECTED_SEND_FAST
            fastConnection?.send(UDPClientAckMessage(session.connectedPeerId).toPacket())
        }
    }

    private fun initiateUDPHandshake(iteration:Int)
    {
        if (connectionStatus.Status == ConnectionStatus.CONNECTED)
        {
            // The initial handshake message must be sent over udp to prove to the server that
            // we are able to send on that channel.
            fastConnection?.send(UDPConnectMessage(session.connectedPeerId).toPacket())

            // retry in a bit since the message might be lost
            val retryMillis =
                Math.min(2.0.pow(iteration.toDouble()) * INITIAL_UDP_RETRY_MILLIS, MAX_UDP_RETRY_MILLIS.toDouble()).toLong()
			Timer().schedule(retryMillis) {
            	initiateUDPHandshake(iteration + 1)
			}
        }
    }

    internal fun send(message:RTMessage):Int
    {
        val payloadSize = message.Payload?.size ?: 0
        if (payloadSize > MAX_RELIABLE_MESSAGE_BYTES)
        {
            throw Exception("Message payload exceeded maximum size of $MAX_RELIABLE_MESSAGE_BYTES bytes")
        }

        if (connected)
        {
            val packet = message.toPacket()
            packet.sender = session.connectedPeerId

            var shouldUseReliable = true
            if (message.deliveryIntent != DeliveryIntent.Reliable)
            {
                if (connectionStatus.Status != ConnectionStatus.CONNECTED_SEND_FAST &&
                    connectionStatus.Status != ConnectionStatus.CONNECTED_SEND_AND_RECEIVE_FAST)
                {
                    log.info("Client requested fast connection, but not currently setup -- "
                        + "falling back to reliable")
                }
                else if (payloadSize > MAX_FAST_MESSAGE_BYTES)
                {
                    log.info("Client requested fast connection, but payload size exceeds "
                        + "limit of $MAX_FAST_MESSAGE_BYTES bytes -- falling back to reliable")
                }
                else
                {
                    shouldUseReliable = false
                }
            }

            return if (shouldUseReliable) {
                packet.reliable = true
                reliableConnection?.send(packet) ?: Constants.ZERO_MESSAGE_BYTES
            } else {
                packet.reliable = false
                fastConnection?.send(packet) ?: Constants.ZERO_MESSAGE_BYTES
            }
        }
        log.error("Not connected. Unable to send request")
        return Constants.ZERO_MESSAGE_BYTES
	}
}