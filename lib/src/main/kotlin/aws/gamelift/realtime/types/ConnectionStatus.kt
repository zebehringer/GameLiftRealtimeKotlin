package aws.gamelift.realtime.types

enum class ConnectionStatus {
    // Client connection is not initialized. 
    READY,
    // Client is trying to connect/reconnect
    CONNECTING,
    // Client is connected over websockets and waiting for action/messages
    CONNECTED,
    // Client is connected over websockets and has been validated as able to sent UDP to the server
    CONNECTED_SEND_FAST,
    // Client is connected over websockets and has been validated as able to sent UDP to the server
    CONNECTED_SEND_AND_RECEIVE_FAST,
    // Client connection was successfully closed due to an explicit user API call
    DISCONNECTED_CLIENT_CALL,
    // Client connection was closed due to non-user issue
    DISCONNECTED
}