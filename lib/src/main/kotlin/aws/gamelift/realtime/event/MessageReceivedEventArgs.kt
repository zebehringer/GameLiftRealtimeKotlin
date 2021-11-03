package aws.gamelift.realtime.event

import aws.gamelift.realtime.platform.EventArgs

class MessageReceivedEventArgs(val message:String) : EventArgs()