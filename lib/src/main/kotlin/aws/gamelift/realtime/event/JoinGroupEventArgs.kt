package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class JoinGroupEventArgs(sender:Int, val groupId:Int) : BaseEventArgs(sender, Constants.JOIN_GROUP_OP_CODE)