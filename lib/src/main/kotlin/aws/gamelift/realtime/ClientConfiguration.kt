package aws.gamelift.realtime

import aws.gamelift.realtime.network.ConnectionFactory
import aws.gamelift.realtime.types.ConnectionType
import aws.gamelift.realtime.network.ReliableConnectionFactory
import aws.gamelift.realtime.network.FastConnectionFactory

class ClientConfiguration {
	/// <summary>
    /// Type of connection suite to use to connect to GameLift Realtime Servers.
    /// </summary>
    /// <value>The type of the connection.</value>
	var connectionType:ConnectionType = ConnectionType.RT_OVER_WS_UDP_UNSECURED

    /// <summary>
    /// Provides implementation of reliable connections based on the ConnectionType.
    /// </summary>
                                                               /// <value>The reliable connection factory.</value>
    var reliableConnectionFactory:ConnectionFactory = ReliableConnectionFactory()

    /// <summary>
    /// Provides implementation of fast connections based on the ConnectionType.
    /// </summary>
    /// <value>The fast connection factory.</value>
    var fastConnectionFactory:ConnectionFactory = FastConnectionFactory()

    /// <summary>
    /// Create a default ClientConfiguration, which contains the following configuration for the game client:
    /// - Connects to Gamelift Realtime Server
    /// - Establishes reliable connection to server with OkHttp implementation provided by Gamelift
    /// - Establishes fast connection to server with JDK UDP implementation provided by Gamelift
    /// - Reliable connection is Unsecured
    /// - Fast connection is Unsecured
    /// </summary>
    /// <returns>The default game client configuration</returns>
	/*
    [Obsolete("This connection is unsecured! Prefer a secured ConnectionType instead! Note, secure connection " +
        "establishment requires the Realtime server to contain generated certificates. Refer to AWS Gamelift " +
        "documentations for creating a certificate-enabled Script fleet.")]
	*/
    companion object {
		fun default() :ClientConfiguration {
			return ClientConfiguration()
		}
	}
}