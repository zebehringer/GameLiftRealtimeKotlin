package aws.gamelift.realtime

import aws.gamelift.realtime.platform.EventArgs
import aws.gamelift.realtime.platform.EventHandler
import aws.gamelift.realtime.event.DataReceivedEventArgs
import aws.gamelift.realtime.event.ErrorEventArgs
import aws.gamelift.realtime.event.GroupMembershipEventArgs
import aws.gamelift.realtime.platform.NullableArgsEventHandler

abstract class ClientEvents {
	// Connection Events
    val connectionOpen = NullableArgsEventHandler<EventArgs>()
    val connectionClose = NullableArgsEventHandler<EventArgs>()
    val connectionError = EventHandler<ErrorEventArgs>()

    // Communication Events
    val dataReceived = EventHandler<DataReceivedEventArgs>()
    val groupMembershipUpdated = EventHandler<GroupMembershipEventArgs>()

    fun onOpen()
    {
        connectionOpen.invoke(this, null)
    }

    fun onClose()
    {
        connectionClose.invoke(this, null)
    }

    fun onError(e:Exception?)
    {
        connectionError.invoke(this, ErrorEventArgs(e))
    }

    fun onDataReceived(sender:Int, opCode:Int, data:ByteArray?)
    {
        dataReceived.invoke(this, DataReceivedEventArgs(sender, opCode, data))
    }

    fun onGroupMembershipUpdated(groupMembershipEventArgs:GroupMembershipEventArgs)
    {
        groupMembershipUpdated.invoke(this, groupMembershipEventArgs)
    }
}