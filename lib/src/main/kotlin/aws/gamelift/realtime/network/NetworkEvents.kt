package aws.gamelift.realtime.network

import aws.gamelift.realtime.commands.RTMessage
import aws.gamelift.realtime.event.BaseEventArgs
import aws.gamelift.realtime.event.ErrorEventArgs

import aws.gamelift.realtime.platform.EventHandler
import aws.gamelift.realtime.platform.NullableArgsEventHandler

open class NetworkEvents {
	// Connection Events
    val connectionOpen = NullableArgsEventHandler<BaseEventArgs>()
    val connectionClose = NullableArgsEventHandler<BaseEventArgs>()
    val connectionError = EventHandler<ErrorEventArgs>()

    // Communication Events
    val messageReceived = EventHandler<MessageEventArgs>()

    fun onOpen()
    {
        connectionOpen.invoke(this, null)
    }

    fun onClose()
    {
        connectionClose.invoke(this, null)
    }

    fun onConnectionError(e:Exception)
    {
        connectionError.invoke(this, ErrorEventArgs(e))
    }

    fun onMessageReceived(result:RTMessage)
    {
        messageReceived.invoke(this, MessageEventArgs(result))
    }

}