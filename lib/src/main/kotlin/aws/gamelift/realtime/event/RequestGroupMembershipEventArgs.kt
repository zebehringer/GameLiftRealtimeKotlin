package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class RequestGroupMembershipEventArgs(sender:Int, val groupId:Int) : BaseEventArgs(sender, Constants.REQUEST_GROUP_MEMBERSHIP_OP_CODE)