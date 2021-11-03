package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class LeaveGroup(sender:Int, val GroupId:Int) : RTMessage(Constants.LEAVE_GROUP_OP_CODE, sender, Constants.PLAYER_ID_SERVER) {

    override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        packet.leaveGroup = com.gamelift.rt.proto.leaveGroup {
            group = GroupId
        }

        return packet
    }
}