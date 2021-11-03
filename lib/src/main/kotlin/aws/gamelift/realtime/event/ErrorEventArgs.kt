package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class ErrorEventArgs(val exception:Exception?) : BaseEventArgs(Constants.PLAYER_ID_CLIENT_INTERNAL, Constants.PLAYER_CONNECT_OP_CODE)