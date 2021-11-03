package aws.gamelift.realtime.event

import aws.gamelift.realtime.Constants

class GameEndEventArgs(sender:Int) : BaseEventArgs(sender, Constants.GAME_END_OP_CODE)