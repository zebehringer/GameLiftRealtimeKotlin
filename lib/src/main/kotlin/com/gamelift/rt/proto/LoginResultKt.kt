//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: proto/CoreCommands.proto

package com.gamelift.rt.proto;

@kotlin.jvm.JvmSynthetic
public inline fun loginResult(block: com.gamelift.rt.proto.LoginResultKt.Dsl.() -> kotlin.Unit): com.gamelift.rt.proto.CoreCommands.LoginResult =
  com.gamelift.rt.proto.LoginResultKt.Dsl._create(com.gamelift.rt.proto.CoreCommands.LoginResult.newBuilder()).apply { block() }._build()
public object LoginResultKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.gamelift.rt.proto.CoreCommands.LoginResult.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.gamelift.rt.proto.CoreCommands.LoginResult.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.gamelift.rt.proto.CoreCommands.LoginResult = _builder.build()

    /**
     * <code>bool success = 1;</code>
     */
    public var success: kotlin.Boolean
      @JvmName("getSuccess")
      get() = _builder.getSuccess()
      @JvmName("setSuccess")
      set(value) {
        _builder.setSuccess(value)
      }
    /**
     * <code>bool success = 1;</code>
     */
    public fun clearSuccess() {
      _builder.clearSuccess()
    }

    /**
     * <code>string reconnectToken = 2;</code>
     */
    public var reconnectToken: kotlin.String
      @JvmName("getReconnectToken")
      get() = _builder.getReconnectToken()
      @JvmName("setReconnectToken")
      set(value) {
        _builder.setReconnectToken(value)
      }
    /**
     * <code>string reconnectToken = 2;</code>
     */
    public fun clearReconnectToken() {
      _builder.clearReconnectToken()
    }

    /**
     * <code>int32 peerId = 3;</code>
     */
    public var peerId: kotlin.Int
      @JvmName("getPeerId")
      get() = _builder.getPeerId()
      @JvmName("setPeerId")
      set(value) {
        _builder.setPeerId(value)
      }
    /**
     * <code>int32 peerId = 3;</code>
     */
    public fun clearPeerId() {
      _builder.clearPeerId()
    }

    /**
     * <code>int32 fastPort = 4;</code>
     */
    public var fastPort: kotlin.Int
      @JvmName("getFastPort")
      get() = _builder.getFastPort()
      @JvmName("setFastPort")
      set(value) {
        _builder.setFastPort(value)
      }
    /**
     * <code>int32 fastPort = 4;</code>
     */
    public fun clearFastPort() {
      _builder.clearFastPort()
    }

    /**
     * <code>bytes caCert = 5;</code>
     */
    public var caCert: com.google.protobuf.ByteString
      @JvmName("getCaCert")
      get() = _builder.getCaCert()
      @JvmName("setCaCert")
      set(value) {
        _builder.setCaCert(value)
      }
    /**
     * <code>bytes caCert = 5;</code>
     */
    public fun clearCaCert() {
      _builder.clearCaCert()
    }

    /**
     * <code>string connectToken = 6;</code>
     */
    public var connectToken: kotlin.String
      @JvmName("getConnectToken")
      get() = _builder.getConnectToken()
      @JvmName("setConnectToken")
      set(value) {
        _builder.setConnectToken(value)
      }
    /**
     * <code>string connectToken = 6;</code>
     */
    public fun clearConnectToken() {
      _builder.clearConnectToken()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.gamelift.rt.proto.CoreCommands.LoginResult.copy(block: com.gamelift.rt.proto.LoginResultKt.Dsl.() -> kotlin.Unit): com.gamelift.rt.proto.CoreCommands.LoginResult =
  com.gamelift.rt.proto.LoginResultKt.Dsl._create(this.toBuilder()).apply { block() }._build()
