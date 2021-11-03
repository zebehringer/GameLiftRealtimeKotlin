package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger
import java.net.InetSocketAddress
import java.io.ByteArrayInputStream
import com.gamelift.rt.proto.PacketOuterClass.Packet
import org.eclipse.californium.elements.AddressEndpointContext
import org.eclipse.californium.elements.EndpointContext
import org.eclipse.californium.elements.MessageCallback
import org.eclipse.californium.elements.RawData
import org.eclipse.californium.scandium.DTLSConnector
import org.eclipse.californium.scandium.config.DtlsConfig
import org.eclipse.californium.scandium.config.DtlsConnectorConfig
import org.eclipse.californium.scandium.dtls.x509.StaticNewAdvancedCertificateVerifier
import java.security.cert.CertificateFactory

open class DtlsUdpConnection(listenPort:Int, val hostname:String, val caCert:ByteArray, logger:ClientLogger) : UdpConnection(listenPort, logger), MessageCallback {

    private var remoteEndpoint:InetSocketAddress? = null
    private var localEndpoint:InetSocketAddress? = null

    private var dtlsConnector: DTLSConnector? = null


    override fun close()
    {
        try {
            dtlsConnector?.destroy()
        } catch (e:Exception) {
            onConnectionError(e)
        }
    }

    override fun open()
    {
        val local = localEndpoint
        val remote = remoteEndpoint
        if (local != null && remote != null) {
            val certFactory = CertificateFactory.getInstance("X.509")
            ByteArrayInputStream(caCert).use { stream ->
                val cert = certFactory.generateCertificate(stream)
                val calConfig = org.eclipse.californium.elements.config.Configuration()
                calConfig.set(DtlsConfig.DTLS_ROLE, DtlsConfig.DtlsRole.CLIENT_ONLY)
                val dtlsConnCfg = DtlsConnectorConfig.Builder(calConfig)
                    .setAddress(localEndpoint)
                    .setAdvancedCertificateVerifier(
                        StaticNewAdvancedCertificateVerifier.builder()
                        .setTrustedCertificates(cert)
                        .build()
                    )
                    .build()
                val connector = DTLSConnector(dtlsConnCfg)
                connector.setRawDataReceiver {
                    receiveData(it.bytes)
                }
                connector.start()
                dtlsConnector = connector
            }
        }
    }

    override fun dispose(disposing:Boolean) {
        if (disposing) {
            close()
        }
    }

    override fun initializeUdp(localEndpoint:InetSocketAddress, remoteEndpoint:InetSocketAddress) {
        this.remoteEndpoint = remoteEndpoint
        this.localEndpoint = localEndpoint
    }

    private fun receiveData(data:ByteArray) {
        ByteArrayInputStream(data).use{ stream ->
            try {
                onPacketReceived(Packet.parseDelimitedFrom(stream))
            } catch (e:Exception) {
                onConnectionError(e)
            }
        }
    }

    override fun sendData(data:ByteArray, len:Int)
    {
        val dest = remoteEndpoint
        if (dest != null) {
            dtlsConnector?.send(
                RawData.outbound(
                    data, AddressEndpointContext(dest), this, false
                )
            )
        }
    }

    override fun onConnecting() {
        log.debug("DTLS connecting")
    }

    override fun onDtlsRetransmission(flight: Int) {
        log.debug("DTLS retransmition $flight")
    }

    override fun onContextEstablished(context: EndpointContext?) {
        log.debug("DTLS context established")
    }

    override fun onSent() {
        log.debug("DTLS onSent")
    }

    override fun onError(t:Throwable) {
        onConnectionError(Exception(t))
    }
}