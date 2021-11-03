package aws.gamelift.realtime.commands

import aws.gamelift.realtime.Constants

class ClientEvent(sender:Int, opCode:Int, payload:ByteArray? = null) : RTMessage(opCode, sender, Constants.PLAYER_ID_SERVER, Payload = payload)
