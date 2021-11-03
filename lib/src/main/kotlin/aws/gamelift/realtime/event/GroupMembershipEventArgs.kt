package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class GroupMembershipEventArgs(sender:Int, val groupId:Int, val playerIds:IntArray?) : BaseEventArgs(sender, Constants.GROUP_MEMBERSHIP_UPDATE_OP_CODE)
