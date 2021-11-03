package aws.gamelift.realtime.commands

import aws.gamelift.realtime.Constants

class VerifyIdentityResult(peerId:Int, sender:Int, val Success:Boolean, payload:ByteArray) : RTMessage(Constants.VERIFY_IDENTITY_RESPONSE_OP_CODE, sender, TargetPlayer = peerId, Payload = payload) {
	companion object {
    	fun fromProtobuf(sender:Int, payload:ByteArray, message:com.gamelift.rt.proto.CoreCommands.VerifyIdentityResult):VerifyIdentityResult
	    {
	        if (message != null)
	        {
				return VerifyIdentityResult(message.peerId, sender, message.success, payload)
	        }
	
	        return VerifyIdentityResult(Constants.INVALID_PEER_ID, sender, false, payload)
	    }
	}
}