package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger
import aws.gamelift.realtime.types.ConnectionType
import aws.gamelift.realtime.platform.ArgumentException

class ReliableConnectionFactory : ConnectionFactory {
	override fun create(options:ConnectionFactoryOptions, log:ClientLogger):BaseConnection
    {
        validateOptions(options)

        val connectionType = options.ClientConfiguration?.connectionType

        log.info("Creating reliable connection client based on ConnectionType in ClientConfiguration: $connectionType")

        when (connectionType)
        {
            ConnectionType.RT_OVER_WSS_DTLS_TLS12 -> {
                log.info("Using Websocket connection secured by TLS 1.2")
                return OkHttpWebSocketConnection(TlsEnabled = true, log)
    		}

            ConnectionType.RT_OVER_WS_UDP_UNSECURED -> {
                log.info("Using unsecured Websocket connection")
                return OkHttpWebSocketConnection(TlsEnabled = false, log)
    		}

            else -> {
                log.warn("Unsupported ConnectionType: $connectionType. Defaulting to Unsecured Websocket connection")
                return OkHttpWebSocketConnection(TlsEnabled = false, log)
            }
        }
    }

    private fun validateOptions(options:ConnectionFactoryOptions)
    {
        if (options.ClientConfiguration == null)
        {
            throw ArgumentException("Unable to create Reliable connection: ClientConfiguration is undefined.")
        }
    }
}