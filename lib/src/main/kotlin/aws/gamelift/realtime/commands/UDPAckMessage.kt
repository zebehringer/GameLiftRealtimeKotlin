package aws.gamelift.realtime.commands

import aws.gamelift.realtime.Constants
import com.gamelift.rt.proto.PacketOuterClass.Packet

class UDPClientAckMessage(sender:Int) : RTMessage(Constants.UDP_CONNECT_CLIENT_ACK_OP_CODE, sender, TargetPlayer = Constants.PLAYER_ID_SERVER) {
	override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        packet.udpConnect = com.gamelift.rt.proto.uDPConnectMessage {
        }

        return packet
    }
}