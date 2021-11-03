package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class JoinGroup(sender:Int, val GroupId:Int) : RTMessage(Constants.JOIN_GROUP_OP_CODE, sender, Constants.PLAYER_ID_SERVER) {

    override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        packet.joinGroup = com.gamelift.rt.proto.joinGroup {
            group = GroupId
        }

        return packet
    }
}