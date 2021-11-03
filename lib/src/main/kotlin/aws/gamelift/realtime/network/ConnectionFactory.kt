package aws.gamelift.realtime.network

import aws.gamelift.realtime.ClientLogger

interface ConnectionFactory {
	fun create(options:ConnectionFactoryOptions, log:ClientLogger):BaseConnection
}