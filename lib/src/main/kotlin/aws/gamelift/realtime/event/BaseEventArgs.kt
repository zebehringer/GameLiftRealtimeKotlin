package aws.gamelift.realtime.event

import aws.gamelift.realtime.platform.EventArgs

abstract class BaseEventArgs(val sender:Int, val opCode:Int, val data:ByteArray? = null) : EventArgs()