package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class RequestGroupMembership(sender:Int, val GroupId:Int) : RTMessage(Constants.REQUEST_GROUP_MEMBERSHIP_OP_CODE, sender, Constants.PLAYER_ID_SERVER) {

    override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        packet.requestGroupMembership = com.gamelift.rt.proto.requestGroupMembership {
        	group = GroupId
        }

        return packet
    }
}