package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class GameStartEventArgs(sender:Int) : BaseEventArgs(sender, Constants.GAME_START_OP_CODE)