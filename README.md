# This is a Kotlin port of the AWS GameLift Realtime Client SDK (from C#).

It has no dependencies on any Android code, so it can be used in any JVM client.

**NOTE**: DTLS does not currently work. 

The code will fall back to sending reliable messages over TLS WebSocket, so it may appear to work. However, fast/UDP messages sent from the server will not be received.



Example usage:

```
package com.example.gamelift

import android.util.Log
import android.view.View

import java.nio.charset.StandardCharsets.UTF_8

import aws.gamelift.realtime.*
import aws.gamelift.realtime.event.*
import aws.gamelift.realtime.types.*
import aws.gamelift.realtime.platform.EventArgs
import aws.gamelift.realtime.platform.EventHandler

class RealTimeClient(private val label:String, private val ui:GameUI, private val endpoint:String, private val tcpPort:Int, private val localUdpPort:Int, private val playerSessionId:String, private val connectionPayload:ByteArray) {

    companion object {
        const val PLAYER_JOINED: Int = 20
        const val PLAYER_LEFT = 21
        const val SEQUENCE_STARTED = 22
        const val PLAYER_MOVED = 23
        const val FAILURE = 25
        const val START_GAME = 26
    }

    private val client:Client
    private var onCloseReceived = false

    private var score:Int = -1
    val pointEarned = EventHandler<Int>()
    val gameOver = EventHandler<EventArgs>()

    init {
        this.ui.SetAction(::onUIAction)

        val clientConfiguration = ClientConfiguration.default()
        // clientConfiguration.connectionType = ConnectionType.RT_OVER_WSS_DTLS_TLS12

        client = Client(clientConfiguration, LoggerAdapter())
        client.connectionOpen.add(::onOpenEvent)
        client.connectionClose.add(::onCloseEvent)
        client.connectionError.add(::onError)
        client.groupMembershipUpdated.add(::OnGroupMembershipUpdate)
        client.dataReceived.add(::OnDataReceived)
    }

    class LoggerAdapter : ClientLogger() {
        override fun debug(message: String, vararg args: Any) {
            Log.d("GameLift", format(message, args))
        }
        override fun info(message: String, vararg args: Any) {
            Log.i("GameLift", format(message, args))
        }
        override fun warn(message: String, vararg args: Any) {
            Log.w("GameLift", format(message, args))
        }
        override fun error(message: String, vararg args: Any) {
            Log.e("GameLift", format(message, args))
        }
    }

    fun connect() {
        client.connect(endpoint, tcpPort, localUdpPort, ConnectionToken(playerSessionId, connectionPayload))
    }

    fun disconnect()
    {
        if (client.connected)
        {
            client.disconnect()
        }
    }

    fun isConnected():Boolean
    {
        return client.connected
    }

    fun sendMessage(opCode:Int, intent:DeliveryIntent, payload:String)
    {
        client.sendMessage(client.newMessage(opCode, intent=intent, targetPlayer=Constants.PLAYER_ID_SERVER, payload=payload.toByteArray(UTF_8)))
    }

    /**
     * Handle connection open events
     */
    fun onOpenEvent(sender:Any, e:EventArgs?)
    {
        Log.i("RealTimeClient", "websocket open")
    }

    /**
     * Handle connection close events
     */
    fun onCloseEvent(sender:Any, e:EventArgs?)
    {
        onCloseReceived = true
    }

    fun onError(sender:Any, e:ErrorEventArgs?)
    {
        Log.e("RealTimeClient", "Connection error", e?.exception)
    }

    /**
     * Handle Group membership update events
     */
    fun OnGroupMembershipUpdate(sender:Any, e:GroupMembershipEventArgs)
    {
    }

    /**
     *  Handle data received from the Realtime server
     */
    fun OnDataReceived(sender:Any, e:DataReceivedEventArgs)
    {
        when (e.opCode)
        {
            PLAYER_JOINED -> {
                val colorString = e.data?.toString(UTF_8)
                if (colorString != null) {
                    ui.Color = colorString.toUInt(16).toInt()
                }
                ui.Enabled = true
                ui.Reset()
            }
            PLAYER_LEFT -> {
                ui.Enabled = false
            }
            SEQUENCE_STARTED -> {
                Log.i("RealTimeClient", "$label reset")
                // clear any local state
                //ui.Enabled = true
                ui.Reset()
                pointEarned.invoke(this,++score)
            }
            PLAYER_MOVED -> {

            }
            FAILURE -> {
                Log.i("RealTimeClient", "$label showing failure")
                //ui.Enabled = false
                ui.ShowFailure()
                gameOver.invoke(this, e)
                score = -1
            }
            // handle message based on OpCode
            else -> {
                Log.i("RealTimeClient", "other op_code $e.opCode received")
            }
        }
    }

    private fun onUIAction(target:View)
    {
        Log.i("RealTimeClient", "$label moved, score = $score")
        sendMessage(PLAYER_MOVED, DeliveryIntent.Fast, "{\"mover\":\""+client.session.connectedPeerId+"\"}");
    }

}
```
