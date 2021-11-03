package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class UDPConnectMessage(sender:Int) : RTMessage(Constants.UDP_CONNECT_OP_CODE, sender, TargetPlayer = Constants.PLAYER_ID_SERVER) {
	override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        packet.udpConnect = com.gamelift.rt.proto.uDPConnectMessage {
        }

        return packet
    }
}
