package aws.gamelift.realtime.commands

import com.gamelift.rt.proto.PacketOuterClass.Packet
import com.gamelift.rt.proto.packet
import aws.gamelift.realtime.Constants
import aws.gamelift.realtime.types.DeliveryIntent
import com.google.protobuf.ByteString

open class RTMessage(val OpCode:Int, val Sender:Int, val TargetPlayer:Int = Constants.NO_TARGET_PLAYER, val TargetGroup:Int = Constants.NO_TARGET_GROUP, var deliveryIntent:DeliveryIntent = DeliveryIntent.Fast, val Payload:ByteArray? = null) {

    constructor(opCode:Int, sender:Int) : this(opCode, sender, Constants.NO_TARGET_PLAYER, Constants.NO_TARGET_GROUP, DeliveryIntent.Fast, null)

    /// <summary>
    /// Takes the given outer packet and merges it with the specific inner message
    /// </summary>
    /// <param name="packet">The base packet to merge with</param>
    /// <returns>The final packet ready for transmission</returns>
    internal open fun toInnerPacket(packet:Packet.Builder):Packet.Builder
    {
        return packet
    }

    /// <summary>
    /// Wrap base information into packet. 
    /// </summary>
    /// <returns></returns>
    internal open fun toPacket():Packet.Builder
    {
        val packet = packet {
            opCode = OpCode
            sender = Sender
            targetPlayer = if (TargetPlayer != Constants.NO_TARGET_PLAYER) TargetPlayer else 0
            targetGroup = if (TargetGroup != Constants.NO_TARGET_GROUP) TargetGroup else 0
            payload = if (Payload != null && Payload.isNotEmpty()) ByteString.copyFrom(Payload) else ByteString.EMPTY
            reliable = (deliveryIntent == DeliveryIntent.Reliable)
        }

        return toInnerPacket(packet.toBuilder())
    }

	companion object {
	    internal fun fromPacket(packet:Packet):RTMessage
	    {
	        var payload:ByteArray? = null
	        if (packet.payload != null) {
	            payload = packet.payload.toByteArray()
	        }
	        when (packet.opCode)
	        {
	            Constants.LOGIN_RESPONSE_OP_CODE ->
	                return LoginResult.fromProtobuf(packet.sender,
	                                                   payload!!,
	                                                   packet.loginResult)
	
	            Constants.GROUP_MEMBERSHIP_UPDATE_OP_CODE ->
	                return GroupMembershipUpdate.fromProtobuf(packet, packet.groupMembershipUpdate)
	
	            Constants.VERIFY_IDENTITY_RESPONSE_OP_CODE ->
	                return VerifyIdentityResult.fromProtobuf(packet.sender, payload!!, packet.verifyIdentityResult)
	
	            else ->
	                return RTMessage(packet.opCode, packet.sender, packet.targetPlayer, Payload = payload, deliveryIntent = if (packet.reliable) DeliveryIntent.Reliable else DeliveryIntent.Fast)
	        }
		}
    }
}