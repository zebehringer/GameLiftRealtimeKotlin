package aws.gamelift.realtime

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.network.ConnectionStats

interface Connection {
	/// <summary>
    /// 
    /// </summary>
    fun initialize(remoteHost:String, remotePort:Int)
    /// <summary>
    /// 
    /// </summary>
    fun open()
    /// <summary>
    /// 
    /// </summary>
    fun close()

    /// <summary>
    /// 
    /// </summary>
    fun terminate()

    /// <summary>
    /// Send a request to a Lightweight Server
    /// </summary>
    /// <param name="request">The request to send </param> 
    /// <returns>The number of bytes sent</returns>
    fun send(packetBuilder:Packet.Builder):Int

    /// <summary>
    /// Handle as response from a Lightweight Servers
    /// </summary>
    /// <param name="packet">The packet received</param>
    fun onPacketReceived(packet:Packet)

    /// <summary>
    /// Return accumulated statistics on this connection
    /// </summary>
    fun getStats():ConnectionStats

    /// <summary>
    /// Resets accumulated statistics on this connection to zeroes
    /// </summary>
    fun resetStats()
}