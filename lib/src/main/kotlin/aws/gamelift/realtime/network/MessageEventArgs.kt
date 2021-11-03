package aws.gamelift.realtime.network

import aws.gamelift.realtime.commands.RTMessage

import aws.gamelift.realtime.platform.EventArgs

class MessageEventArgs(val Result:RTMessage) : EventArgs()