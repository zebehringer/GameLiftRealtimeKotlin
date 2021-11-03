package aws.gamelift.realtime

interface ServerConnection {
	fun connect()

    fun send(data:String):String

    fun close()
}