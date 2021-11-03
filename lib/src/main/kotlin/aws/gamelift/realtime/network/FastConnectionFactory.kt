package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger
import aws.gamelift.realtime.types.ConnectionType
import aws.gamelift.realtime.platform.ArgumentException
import aws.gamelift.realtime.platform.ArgumentOutOfRangeException

class FastConnectionFactory : ConnectionFactory {
/// <summary>
    /// Creates unsecure/secure UDP connection client based on the 
    /// URL scheme. Url starting with "https://" creates secure client,
    /// otherwise, an unsecure client is created.
    /// 
    /// Both ListenPort and Url are required to be initialized in
    /// ConnectionFactoryOptions.
    /// </summary>
    override fun create(options:ConnectionFactoryOptions, log:ClientLogger):BaseConnection
    {
        validateOptions(options)

        val connectionType = options.ClientConfiguration?.connectionType

        log.info("Creating fast connection client based on ConnectionType in ClientConfiguration: $connectionType")

        when (connectionType)
        {
            ConnectionType.RT_OVER_WSS_DTLS_TLS12 -> {
                val caCert = options.CaCert
                if (caCert == null) {
                    throw Exception("CA Cert not valid")
                } else {
                    log.info("Using UDP connection secured by DTLS 1.2")
                    return DtlsUdpConnection(options.UdpListenPort, options.HostName, caCert, log)
                }
            }
            // This switch case also includes RT_OVER_WEBSOCKET since its has the same value as RT_OVER_WS_UDP_UNSECURED
            ConnectionType.RT_OVER_WS_UDP_UNSECURED -> {
                log.info("Using unsecured UDP connection")
                return PlatformUdpConnection(options.UdpListenPort, log)
            }

            else -> {
                log.warn("Unsupported ConnectionType: $connectionType. Defaulting to Unsecured UDP connection")
                return PlatformUdpConnection(options.UdpListenPort, log)
            }
        }
    }

    private fun validateOptions(options:ConnectionFactoryOptions)
    {
        if (options.UdpListenPort < 1 || options.UdpListenPort > 65535)
        {
            throw ArgumentOutOfRangeException("Unable to create fast connection: " +
                   "UDP listen port number is out of range. Expected: 1-65535. Actual: $options.UdpListenPort")
        }

        if (options.HostName.isEmpty())
        {
            throw ArgumentException("Unable to create fast connection: host name is undefined")
        }

        if (options.ClientConfiguration == null)
        {
            throw ArgumentException("Unable to create fast connection: client configuration is undefined")
        }

        // Unsecured fast clients do not need to use CA certificate
        if (options.ClientConfiguration.connectionType != ConnectionType.RT_OVER_WS_UDP_UNSECURED &&
            (options.CaCert == null || options.CaCert?.size == 0))
        {
            throw ArgumentException("Unable to create secured fast connection: CA cert is undefined")
        }
    }
}