package aws.gamelift.realtime.event

class DataReceivedEventArgs(sender:Int, opCode:Int, data:ByteArray?) : BaseEventArgs(sender, opCode, data)