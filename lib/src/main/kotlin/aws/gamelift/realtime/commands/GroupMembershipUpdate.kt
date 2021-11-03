package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class GroupMembershipUpdate(sender:Int, targetPlayer:Int, val GroupId:Int, val PlayerIds:IntArray) : RTMessage(Constants.GROUP_MEMBERSHIP_UPDATE_OP_CODE, sender, TargetPlayer = targetPlayer) {

	companion object {
		fun fromProtobuf(packet:Packet, message:com.gamelift.rt.proto.GroupMessages.GroupMembershipUpdate):GroupMembershipUpdate
	    {
	        if (message != null)
	        {
	            return GroupMembershipUpdate(packet.sender, packet.targetPlayer, message.group, message.playersList.toIntArray())
	        }
	
	        return GroupMembershipUpdate(packet.sender, packet.targetPlayer, Constants.NO_TARGET_GROUP, IntArray(0))
	    }
	}

    override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        val groupMembershipUpdate = com.gamelift.rt.proto.groupMembershipUpdate {
        	group = GroupId
        	players.addAll(PlayerIds.toList())
        }

		packet.groupMembershipUpdate = groupMembershipUpdate

        return packet
    }
}