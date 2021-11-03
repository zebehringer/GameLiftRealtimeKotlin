package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientConfiguration

data class ConnectionFactoryOptions(
	val HostName:String,
	val UdpListenPort:Int,
	var CaCert:ByteArray? = null,
	val ClientConfiguration:ClientConfiguration?
)