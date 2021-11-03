package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger
import aws.gamelift.realtime.Connection
import aws.gamelift.realtime.commands.RTMessage

import com.gamelift.rt.proto.PacketOuterClass.Packet
import com.google.protobuf.CodedOutputStream
import java.io.ByteArrayOutputStream
import aws.gamelift.realtime.platform.IDisposable

abstract class BaseConnection(protected val log:ClientLogger) : NetworkEvents(), Connection, IDisposable {
	private val connStats:ConnectionStats = ConnectionStats()


        /// <summary>
        /// Initializes a connection with a server
        /// </summary>
        /// <param name="hostName">hostName of server</param>
        /// <param name="port">port of server</param>
        //public abstract fun Initialize(hostName:String, port:Int)

        /// <summary>
        /// Open the initialized connection with the server.
        /// This should be called after Initialize(string, string)
        /// </summary>
        //public abstract fun Open()

        /// <summary>
        /// Called before packets are sent to the server.
        /// </summary>
        /// <param name="packet">Packet to be sent</param>
        protected open fun beforeSend(packetBuilder:Packet.Builder) { }

        /// <summary>
        /// Decides whether if the packet can be sent to the server.
        /// </summary>
        /// <returns><c>true</c>, if packet can be sent, <c>false</c> otherwise.</returns>
        /// <param name="packet">Packet to be sent</param>
        protected open fun canSend(packet:Packet):Boolean {
			return true
		}

        /// <summary>
        /// Called after packets are sent to the server.
        /// </summary>
        /// <param name="packet">Packet sent</param>
        protected open fun afterSend(packet:Packet) { }

        /// <summary>
        /// Sends data to server using the established connection.
        /// Note: The data was transformed to byte array using Protobuf.
        /// </summary>
        /// <param name="data">Raw byte array of packet</param>
        /// <param name="len">Length of data</param>
        protected abstract fun sendData(data:ByteArray, len:Int)

        /// <summary>
        /// Called before packets are received.
        /// </summary>
        /// <param name="packet">Packet to be received</param>
        protected open fun beforeReceive(packet:Packet) { }

        /// <summary>
        /// Decides whether if packets can be received from the server.
        /// </summary>
        /// <returns><c>true</c>, if client can receive the packet, <c>false</c> otherwise.</returns>
        /// <param name="packet">Packet.</param>
        protected open fun canReceive(packet:Packet):Boolean {
			return true
		}

        /// <summary>
        /// Called after packets are received.
        /// NOTE: for business logics, the packet should be handled by OnMessageReceived(),
        ///       this method is used for clean up if necessary.
        /// </summary>
        /// <param name="packet">Packet received</param>
        protected open fun afterReceive(packet:Packet) { }

        /// <summary>
        /// Close the established connection.
        /// </summary>
        //public abstract fun Close()

        /// <summary>
        /// Dispose the connection if needed.
        /// </summary>
        /// <param name="disposing">If set to <c>true</c> dispose the connection.</param>
        //protected abstract fun Dispose(disposing:Boolean)

        /// <summary>
        /// Send a packet to server.
        /// Note: unless you want to serialize the data using methods other than protobuf,
        ///       override SendData(byte[], int) instead.
        /// </summary>
        /// <returns>The send.</returns>
        /// <param name="packet">Packet.</param>
        override fun send(packetBuilder:Packet.Builder):Int
        {
            try
            {
                beforeSend(packetBuilder)

                val packet = packetBuilder.build()

                if (!canSend(packet))
                {
                    return -1
                }

                //val messageSize = packet.getSerializedSize()
                ByteArrayOutputStream().use { ms ->
                    val output = CodedOutputStream.newInstance(ms)
                    output.writeMessageNoTag(packet)
                    //val ret = output.Position
                    output.flush()

                    val data = ms.toByteArray()
                    log.info("Sending packet with size ${data.size}, opcode: ${packet.opCode}, content: $packet")
                    sendData(data, data.size)
                    connStats.recordMessageSent()

                    afterSend(packet)

                    return data.size
                }
            }
            catch (e:Exception)
            {
                log.error("Exception occurred sending data. Exception: ${e.message}")
            }
            return -1
        }

        /// <summary>
        /// Called when a packet is received.
        /// Note: unless you want to serialize the data using methods other than protobuf,
        ///       override OnMessageReceived() instead to handle the packet that was 
        ///       serialized by protobuf.
        /// </summary>
        /// <param name="packet">Packet.</param>
        override fun onPacketReceived(packet:Packet)
        {
            connStats.recordMessageReceived()

            beforeReceive(packet)

            if (!canReceive(packet))
            {
                log.warn("Packet was not allowed to be received.")
                return
            }

            val response = RTMessage.fromPacket(packet)

            onMessageReceived(response)

            afterReceive(packet)
        }

        /// <summary>
        /// Gets the connection stats.
        /// </summary>
        /// <returns>connection stats</returns>
        override fun getStats():ConnectionStats
        {
            return connStats.getCopy()
        }

        /// <summary>
        /// Resets the connection stats.
        /// </summary>
        override fun resetStats()
        {
            connStats.reset()
        }

        /// <summary>
        /// Terminate the connection.
        /// </summary>
        override fun terminate()
        {
            close()
        }

        /// <summary>
        /// Releases all resource used by the <see cref="T:Aws.GameLift.Realtime.Network.BaseConnection"/> object.
        /// </summary>
        /// <remarks>Call <see cref="Dispose"/> when you are finished using the
        /// <see cref="T:Aws.GameLift.Realtime.Network.BaseConnection"/>. The <see cref="Dispose"/> method leaves the
        /// <see cref="T:Aws.GameLift.Realtime.Network.BaseConnection"/> in an unusable state. After calling
        /// <see cref="Dispose"/>, you must release all references to the
        /// <see cref="T:Aws.GameLift.Realtime.Network.BaseConnection"/> so the garbage collector can reclaim the memory
        /// that the <see cref="T:Aws.GameLift.Realtime.Network.BaseConnection"/> was occupying.</remarks>

        open fun dispose()
        {
            dispose(true)
            //GC.SuppressFinalize(this)
        }

}
