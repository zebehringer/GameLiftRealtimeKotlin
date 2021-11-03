package aws.gamelift.realtime.network

import java.net.URI
import aws.gamelift.realtime.ClientLogger

abstract class WebSocketConnection(private val TlsEnabled:Boolean = false, logger:ClientLogger) : BaseConnection(logger) {
	// TLS connection is disabled by default for backward compatibility reasons, because this feature was not
    // available when RTS was initially released. To create a secured websocket connection, set this value to true.

    override fun initialize(remoteHost:String, remotePort:Int)
    {
        val webSocketUri = buildSocketUri(remoteHost, remotePort)
        log.info("Initializing websocket. Uri: $webSocketUri")

        initializeWebSocket(webSocketUri.toString())
    }

    protected abstract fun initializeWebSocket(uri:String)

    private fun buildSocketUri(host:String, port:Int):URI
    {
        if (host.isEmpty())
        {
            throw Exception("Cannot construct socket uri: host is undefined")
        }

        if (port <= 0 || port > 65535)
        {
            throw Exception(
                "Cannot construct socket uri: port $port is out of range. Expected 1-65535."
			)
        }

        val scheme = if (TlsEnabled) "wss" else "ws"

        // Build RTS's websocket address which is in the following formats:
        // ws://hostname/websocket OR wss://hostname/websocket
        return URI(scheme, null, host, port, "/websocket", null, null)
    }
}