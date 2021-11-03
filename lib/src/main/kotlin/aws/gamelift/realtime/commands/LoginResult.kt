package aws.gamelift.realtime.commands

import aws.gamelift.realtime.Constants

class LoginResult(peerId:Int, sender:Int, val Success:Boolean, val ConnectToken:String, val ReconnectToken:String?, val UdpPort:Int, val CaCert:ByteArray?, payload:ByteArray)
		: RTMessage(Constants.LOGIN_RESPONSE_OP_CODE, sender, TargetPlayer = peerId, Payload = payload) {

	companion object {
		fun fromProtobuf(sender:Int, payload:ByteArray, message:com.gamelift.rt.proto.CoreCommands.LoginResult):LoginResult {
	        if (message != null)
	        {
	        	return LoginResult(message.peerId, sender, message.success, message.connectToken, message.reconnectToken, message.fastPort, message.caCert.toByteArray(), payload)
	        }
	
	        return LoginResult(Constants.INVALID_PEER_ID, sender, false, "", null, Constants.INVALID_PORT, null, payload);
		}
    }
}