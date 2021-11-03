package aws.gamelift.realtime.network

import java.net.InetSocketAddress
import aws.gamelift.realtime.ClientLogger
import com.gamelift.rt.proto.PacketOuterClass.Packet
import java.io.ByteArrayInputStream
import java.net.DatagramSocket
import java.util.concurrent.atomic.AtomicBoolean
import java.net.DatagramPacket
import java.net.SocketTimeoutException

open class PlatformUdpConnection(listenPort:Int, logger:ClientLogger) : UdpConnection(listenPort, logger) {
	private var udp:UdpClient? = null
    private var remoteEndpoint:InetSocketAddress? = null

    inner class UdpClient(localPort:Int) : Thread() {
		val socket = DatagramSocket(localPort)
		val running = AtomicBoolean(true)
		val receiving = AtomicBoolean(false)
		private var retries = 3
		override fun run() {
			while (running.get()) {
				if (receiving.get()) {
                    try {
                        val message = ByteArray(8000)
                        val packet = DatagramPacket(message, message.size)
                        log.info("UDP client: about to wait to receive")
                        socket.soTimeout = 10000
                        socket.receive(packet)
                        receiving.set(false)
                        // TODO should this be invoked on a separate thread?
                        receiveData(packet.data, this)
                        retries = 3
                    } catch (t:SocketTimeoutException) {
                        if (retries-- == 0) {
                            running.set(false)
                            log.warn("UDP client: failed to receive packet after 3 tries")
                        }
                    }
				} else {
					sleep(100)
				}
			}
		}
	}

    override fun dispose(disposing:Boolean)
    {
        if (disposing)
        {
            close()
        }
    }

    override fun initializeUdp(localEndpoint:InetSocketAddress, remoteEndpoint:InetSocketAddress)
    {
        this.remoteEndpoint = remoteEndpoint

        val udpClient = createUdpClient(localEndpoint)

        this.udp = udpClient

        udpClient.start()
        udpClient.receiving.set(true)
    }

    protected open fun createUdpClient(ipEndPoint:InetSocketAddress):UdpClient
    {
        return UdpClient(ipEndPoint.port)
    }

    private fun receiveData(data:ByteArray, udpClient:UdpClient)
    {
        ByteArrayInputStream(data).use{ stream ->
            try
            {
                onPacketReceived(Packet.parseDelimitedFrom(stream))
            }
            finally
            {
                udpClient.receiving.set(true)
            }
        }
    }

    override fun sendData(data:ByteArray, len:Int)
    {
    	val dest = remoteEndpoint
    	if (dest != null) {
    		udp?.socket?.send(DatagramPacket(data, data.size, dest.address, dest.port))
    	}
    }

    override fun open()
    {
    }

    override fun close()
    {
    	udp?.running?.set(false)
        udp?.socket?.close()
    }
}