package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class LeaveGroupEventArgs(sender:Int, val groupId:Int) : BaseEventArgs(sender, Constants.LEAVE_GROUP_OP_CODE)