package aws.gamelift.realtime

data class ConnectionToken(val PlayerSessionId:String, val Payload:ByteArray)