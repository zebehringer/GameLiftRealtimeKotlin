package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class VerifyIdentityCommand(val PeerId:Int, val ConnectToken:String, payload:ByteArray?, sender:Int) : RTMessage(Constants.VERIFY_IDENTITY_OP_CODE, sender, Payload = payload){
	override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        // Set optional verify identity command
        packet.verifyIdentity = com.gamelift.rt.proto.verifyIdentityCommand {
            peerId = PeerId
            connectToken = ConnectToken
        }

        return packet
    }
}