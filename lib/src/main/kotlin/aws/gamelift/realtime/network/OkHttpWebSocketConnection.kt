package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger
import com.gamelift.rt.proto.PacketOuterClass.Packet
import java.io.ByteArrayInputStream
import com.google.protobuf.util.JsonFormat
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import okio.ByteString
import okio.ByteString.Companion.toByteString
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ConnectionSpec
import okhttp3.TlsVersion
import java.util.Collections
import javax.net.ssl.SSLSession

class OkHttpWebSocketConnection(TlsEnabled:Boolean, logger: ClientLogger) : WebSocketConnection(TlsEnabled, logger) {
	//protected var messageReceiveEvent:AutoResetEvent? = AutoResetEvent(false)
    var lastMessageReceived:String = ""
    var webSocket:WebSocket? = null
    val tlsSpecs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .tlsVersions(TlsVersion.TLS_1_2)
        .allEnabledCipherSuites()
        .build()
    private val cleartextSpecs = ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build()
    val client:OkHttpClient = if (TlsEnabled) {
        OkHttpClient.Builder()
            .connectionSpecs(Collections.singletonList(tlsSpecs))
            .hostnameVerifier{ _:String, _:SSLSession->true}
            .build()
    } else {
        OkHttpClient.Builder().connectionSpecs(Collections.singletonList(cleartextSpecs)).build()
    }

    private val listener = object : WebSocketListener() {
    	override fun onOpen(webSocket:WebSocket, response:Response) {
    		onOpen()
    	}
    	override fun onMessage(webSocket:WebSocket, text:String) {
	        log.info("Connection - Message received $text")

	        lastMessageReceived = text
	        // messageReceiveEvent.Set()
	
	        // Assume this is protobuf via json - unexpected delivery path
	        val packet = Packet.newBuilder()
	        JsonFormat.parser().merge(text, packet)
	
	        // Signal to listeners
	        onPacketReceived(packet.build())

    	}
    	override fun onMessage(webSocket:WebSocket, bytes:ByteString) {
    		val data = bytes.toByteArray()
	        // messageReceiveEvent.Set()
	
	        ByteArrayInputStream(data).use { stream ->
	            onPacketReceived(Packet.parseDelimitedFrom(stream))
	        }
    	}
    	override fun onClosing(webSocket:WebSocket, code:Int, reason:String) {
    		webSocket.close(1000, null)
		}
    	override fun onFailure(webSocket:WebSocket, t:Throwable, response:Response?) {
            onConnectionError(Exception(t))
    	}
    }

    override fun dispose(disposing:Boolean)
    {
        if (disposing)
        {
            /*if (messageReceiveEvent != null)
            {
                messageReceiveEvent.Close()
                messageReceiveEvent = null
            }
             */
            webSocket?.close(1000, "Socket Closed")
        }
    }

    override fun initializeWebSocket(uri:String)
    {
        webSocket = createWebSocket(uri)
    }

    fun createWebSocket(uri:String):WebSocket
    {
        // If URI starts with "ws://", client will create a unsecured ws connection.
        // If URI starts with "wss://", client will negotiate with server to create a ws connection secured with 
        // TLS v1.2, other versions will be rejected.
        // See Websocket4Net documentation: https://tiny.amazon.com/1ezl413rl/githkerrWebSblob9a5eWebSWebS
        val websocket4NetWebSocket = client.newWebSocket(
        		Request.Builder().url(uri).build(),
        		listener
        )

        // TODO If testing with self-signed certs, AllowUnstrustedCertificate needs to be set to true
        /*websocket4NetWebSocket.Security.AllowUnstrustedCertificate = false
        websocket4NetWebSocket.Security.AllowNameMismatchCertificate = false
        websocket4NetWebSocket.Security.AllowCertificateChainErrors = false*/

        return websocket4NetWebSocket
    }

    override fun open()
    {
        // should already be open
    }

    override fun sendData(data:ByteArray, len:Int)
    {
        webSocket?.send(data.toByteString(0, len))
    }

    override fun close()
    {
         webSocket?.close(1000, "Socket Closed")
    }

}