package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import aws.gamelift.realtime.Constants

class LoginCommand(val playerSessionId:String?, payload:ByteArray?, sender:Int) : RTMessage(Constants.LOGIN_OP_CODE, sender, Constants.PLAYER_ID_SERVER, Payload = payload) {

    override fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        packet.login = com.gamelift.rt.proto.loginCommand {
            clientVersion = Constants.CLIENT_VERSION
            playerSessionId = playerSessionId
        }

        return packet
    }

}