package aws.gamelift.realtime.types

enum class ConnectionType {
	/// <summary>
    /// Connect to Realtime server with unsecured websocket and UDP.
    /// </summary>
    RT_OVER_WS_UDP_UNSECURED,

    /// <summary>
    /// Connect to Realtime server with websocket and DTLS secured by TLS 1.2.
    /// </summary>
    RT_OVER_WSS_DTLS_TLS12
}
