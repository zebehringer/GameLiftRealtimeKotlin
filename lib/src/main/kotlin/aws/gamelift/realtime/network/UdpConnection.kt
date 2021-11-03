package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger
import com.gamelift.rt.proto.PacketOuterClass.Packet
import java.util.concurrent.atomic.AtomicInteger
import java.net.InetAddress
import java.net.Inet6Address
import java.net.InetSocketAddress

abstract class UdpConnection(private val listenPort:Int, logger:ClientLogger) : BaseConnection(logger) {

        // For validating packet order (older packets are dropped)
        private val clientSequence = AtomicInteger(-1)
        private var serverSequence:Int = -1

        override fun initialize(remoteHost:String, remotePort:Int)
        {
            log.info("Initializing udp. Host: $remoteHost, Remote Port: $remotePort, Listen Port: $listenPort")

            val addresses = InetAddress.getAllByName(remoteHost)

            if (addresses.isEmpty())
            {
                throw Exception("Unable to lookup remote host: $remoteHost")
            }
            else if (addresses.size > 1)
            {
                // It's possible for addresses to have multiple values if the hostname is mapped to both IPv6 and IPv4,
                // In those cases, IPv6 will take precedence over IPv4 address.

                log.info("Multiple IP addresses: [${addresses.joinToString(",")}] found for hostname: $remoteHost. Selecting the first result: ${addresses[0]}.")
            }

            val remoteAddress = addresses[0]
            val localAddress = if (remoteAddress is Inet6Address) InetAddress.getByAddress(ByteArray(6)) else InetAddress.getByAddress(ByteArray(4))

            val remoteEndpoint = InetSocketAddress(remoteAddress, remotePort)
            val localEndpoint = InetSocketAddress(localAddress, listenPort)

            log.info("Establishing UDP connection between server endpoint: $remoteEndpoint and local endpoint: $localEndpoint")

            initializeUdp(localEndpoint, remoteEndpoint)
        }

        protected abstract fun initializeUdp(localEndpoint:InetSocketAddress, remoteEndpoint:InetSocketAddress)

        override fun beforeSend(packetBuilder:Packet.Builder)
        {
            packetBuilder.sequenceNumber = clientSequence.incrementAndGet()
        }

        override fun canReceive(packet:Packet):Boolean
        {
            synchronized (serverSequence)
            {
                if (packet.sequenceNumber <= serverSequence)
                {
                    // Old packet -- drop it
                    return false
                }

                serverSequence = packet.sequenceNumber
            }

            return true
        }
}