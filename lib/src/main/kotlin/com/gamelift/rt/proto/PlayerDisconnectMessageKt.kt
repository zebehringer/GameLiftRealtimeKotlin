//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: proto/CoreCommands.proto

package com.gamelift.rt.proto;

@kotlin.jvm.JvmSynthetic
public inline fun playerDisconnectMessage(block: com.gamelift.rt.proto.PlayerDisconnectMessageKt.Dsl.() -> kotlin.Unit): com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage =
  com.gamelift.rt.proto.PlayerDisconnectMessageKt.Dsl._create(com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage.newBuilder()).apply { block() }._build()
public object PlayerDisconnectMessageKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage = _builder.build()

    /**
     * <code>int32 peerId = 1;</code>
     */
    public var peerId: kotlin.Int
      @JvmName("getPeerId")
      get() = _builder.getPeerId()
      @JvmName("setPeerId")
      set(value) {
        _builder.setPeerId(value)
      }
    /**
     * <code>int32 peerId = 1;</code>
     */
    public fun clearPeerId() {
      _builder.clearPeerId()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage.copy(block: com.gamelift.rt.proto.PlayerDisconnectMessageKt.Dsl.() -> kotlin.Unit): com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage =
  com.gamelift.rt.proto.PlayerDisconnectMessageKt.Dsl._create(this.toBuilder()).apply { block() }._build()
