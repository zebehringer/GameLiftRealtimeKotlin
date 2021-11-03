package aws.gamelift.realtime

class Constants {
	companion object {
		// Client caller Version
	    const val CLIENT_VERSION = 2
	
	    // Player ids
	    const val PLAYER_ID_SERVER = -1
	    const val PLAYER_ID_CLIENT_INTERNAL = -2
	    // actual players that connect are assigned positive ids
	
	    // Group ids
	    const val GROUP_ID_ALL_PLAYERS = -1
	    // developer-defined ones are all positive
	
	    // Op Codes
	    // internal op codes are <= 0   
	    const val NULL_OP_CODE = -99999
	
	    const val LOGIN_OP_CODE = 0
	    const val LOGIN_RESPONSE_OP_CODE = -1
	    const val PING_RESULT_OP_CODE = -3
	    // UDP connect flow is:
	    // client -> server: UDP_CONNECT_OP_CODE
	    // server -> client: UDP_CONNECT_SERVER_ACK_OP_CODE (confirms server received initial msg)
	    // client -> server: UDP_CONNECT_CLIENT_ACK_OP_CODE (confirms client received server ack)
	    const val UDP_CONNECT_OP_CODE = -5
	    const val UDP_CONNECT_SERVER_ACK_OP_CODE = -6
	    const val UDP_CONNECT_CLIENT_ACK_OP_CODE = -7
	    const val PLAYER_READY_OP_CODE = -8
	    const val JOIN_GROUP_OP_CODE = -10
	    const val LEAVE_GROUP_OP_CODE = -11
	    const val REQUEST_GROUP_MEMBERSHIP_OP_CODE = -12
	    const val GROUP_MEMBERSHIP_UPDATE_OP_CODE = -13
	    const val VERIFY_IDENTITY_OP_CODE = -14
	    const val VERIFY_IDENTITY_RESPONSE_OP_CODE = -15
	    const val PLAYER_CONNECT_OP_CODE = -101
	    const val PLAYER_DISCONNECT_OP_CODE = -103
	
	    // server-initiated messages
	    const val GAME_START_OP_CODE = -1000
	    const val GAME_END_OP_CODE = -1001
	
	    // client-initiated messages
	    // developer-defined op codes are all > 0
	    const val SEND_MESSAGE_OP_CODE = 1
	
	    const val INVALID_PORT = -1
	
	    // An invalid peerId, typically means player is unconnected to GameLift Realtime
	    const val INVALID_PEER_ID = 0
	    const val NO_TARGET_PLAYER = 0
	    const val NO_TARGET_GROUP = 0
	    const val ZERO_MESSAGE_BYTES = 0
	}
}