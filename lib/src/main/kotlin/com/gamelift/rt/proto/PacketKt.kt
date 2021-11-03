//Generated by the protocol buffer compiler. DO NOT EDIT!
// source: proto/Packet.proto

package com.gamelift.rt.proto;

@kotlin.jvm.JvmSynthetic
public inline fun packet(block: com.gamelift.rt.proto.PacketKt.Dsl.() -> kotlin.Unit): com.gamelift.rt.proto.PacketOuterClass.Packet =
  com.gamelift.rt.proto.PacketKt.Dsl._create(com.gamelift.rt.proto.PacketOuterClass.Packet.newBuilder()).apply { block() }._build()
public object PacketKt {
  @kotlin.OptIn(com.google.protobuf.kotlin.OnlyForUseByGeneratedProtoCode::class)
  @com.google.protobuf.kotlin.ProtoDslMarker
  public class Dsl private constructor(
    private val _builder: com.gamelift.rt.proto.PacketOuterClass.Packet.Builder
  ) {
    public companion object {
      @kotlin.jvm.JvmSynthetic
      @kotlin.PublishedApi
      internal fun _create(builder: com.gamelift.rt.proto.PacketOuterClass.Packet.Builder): Dsl = Dsl(builder)
    }

    @kotlin.jvm.JvmSynthetic
    @kotlin.PublishedApi
    internal fun _build(): com.gamelift.rt.proto.PacketOuterClass.Packet = _builder.build()

    /**
     * <code>sint32 opCode = 1;</code>
     */
    public var opCode: kotlin.Int
      @JvmName("getOpCode")
      get() = _builder.getOpCode()
      @JvmName("setOpCode")
      set(value) {
        _builder.setOpCode(value)
      }
    /**
     * <code>sint32 opCode = 1;</code>
     */
    public fun clearOpCode() {
      _builder.clearOpCode()
    }

    /**
     * <code>int32 sequenceNumber = 2;</code>
     */
    public var sequenceNumber: kotlin.Int
      @JvmName("getSequenceNumber")
      get() = _builder.getSequenceNumber()
      @JvmName("setSequenceNumber")
      set(value) {
        _builder.setSequenceNumber(value)
      }
    /**
     * <code>int32 sequenceNumber = 2;</code>
     */
    public fun clearSequenceNumber() {
      _builder.clearSequenceNumber()
    }

    /**
     * <code>int32 targetPlayer = 3;</code>
     */
    public var targetPlayer: kotlin.Int
      @JvmName("getTargetPlayer")
      get() = _builder.getTargetPlayer()
      @JvmName("setTargetPlayer")
      set(value) {
        _builder.setTargetPlayer(value)
      }
    /**
     * <code>int32 targetPlayer = 3;</code>
     */
    public fun clearTargetPlayer() {
      _builder.clearTargetPlayer()
    }

    /**
     * <code>int32 targetGroup = 4;</code>
     */
    public var targetGroup: kotlin.Int
      @JvmName("getTargetGroup")
      get() = _builder.getTargetGroup()
      @JvmName("setTargetGroup")
      set(value) {
        _builder.setTargetGroup(value)
      }
    /**
     * <code>int32 targetGroup = 4;</code>
     */
    public fun clearTargetGroup() {
      _builder.clearTargetGroup()
    }

    /**
     * <code>int32 sender = 5;</code>
     */
    public var sender: kotlin.Int
      @JvmName("getSender")
      get() = _builder.getSender()
      @JvmName("setSender")
      set(value) {
        _builder.setSender(value)
      }
    /**
     * <code>int32 sender = 5;</code>
     */
    public fun clearSender() {
      _builder.clearSender()
    }

    /**
     * <code>bool reliable = 6;</code>
     */
    public var reliable: kotlin.Boolean
      @JvmName("getReliable")
      get() = _builder.getReliable()
      @JvmName("setReliable")
      set(value) {
        _builder.setReliable(value)
      }
    /**
     * <code>bool reliable = 6;</code>
     */
    public fun clearReliable() {
      _builder.clearReliable()
    }

    /**
     * <pre>
     * Version of the Realtime Client (i.e. NOT the customer's game)
     * </pre>
     *
     * <code>int32 clientVersion = 7;</code>
     */
    public var clientVersion: kotlin.Int
      @JvmName("getClientVersion")
      get() = _builder.getClientVersion()
      @JvmName("setClientVersion")
      set(value) {
        _builder.setClientVersion(value)
      }
    /**
     * <pre>
     * Version of the Realtime Client (i.e. NOT the customer's game)
     * </pre>
     *
     * <code>int32 clientVersion = 7;</code>
     */
    public fun clearClientVersion() {
      _builder.clearClientVersion()
    }

    /**
     * <pre>
     * payload is for customer-provided blob-style data that is not modeled in anyway and simply passed through
     *:deserialize=instance.ReadPayload(stream)
     *:serialize=instance.WritePayload(stream);
     * </pre>
     *
     * <code>bytes payload = 15;</code>
     */
    public var payload: com.google.protobuf.ByteString
      @JvmName("getPayload")
      get() = _builder.getPayload()
      @JvmName("setPayload")
      set(value) {
        _builder.setPayload(value)
      }
    /**
     * <pre>
     * payload is for customer-provided blob-style data that is not modeled in anyway and simply passed through
     *:deserialize=instance.ReadPayload(stream)
     *:serialize=instance.WritePayload(stream);
     * </pre>
     *
     * <code>bytes payload = 15;</code>
     */
    public fun clearPayload() {
      _builder.clearPayload()
    }

    /**
     * <pre>
     * General Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.LoginCommand login = 30;</code>
     */
    public var login: com.gamelift.rt.proto.CoreCommands.LoginCommand
      @JvmName("getLogin")
      get() = _builder.getLogin()
      @JvmName("setLogin")
      set(value) {
        _builder.setLogin(value)
      }
    /**
     * <pre>
     * General Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.LoginCommand login = 30;</code>
     */
    public fun clearLogin() {
      _builder.clearLogin()
    }
    /**
     * <pre>
     * General Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.LoginCommand login = 30;</code>
     * @return Whether the login field is set.
     */
    public fun hasLogin(): kotlin.Boolean {
      return _builder.hasLogin()
    }

    /**
     * <code>.com.gamelift.rt.proto.LoginResult loginResult = 31;</code>
     */
    public var loginResult: com.gamelift.rt.proto.CoreCommands.LoginResult
      @JvmName("getLoginResult")
      get() = _builder.getLoginResult()
      @JvmName("setLoginResult")
      set(value) {
        _builder.setLoginResult(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.LoginResult loginResult = 31;</code>
     */
    public fun clearLoginResult() {
      _builder.clearLoginResult()
    }
    /**
     * <code>.com.gamelift.rt.proto.LoginResult loginResult = 31;</code>
     * @return Whether the loginResult field is set.
     */
    public fun hasLoginResult(): kotlin.Boolean {
      return _builder.hasLoginResult()
    }

    /**
     * <code>.com.gamelift.rt.proto.PingCommand ping = 32;</code>
     */
    public var ping: com.gamelift.rt.proto.CoreCommands.PingCommand
      @JvmName("getPing")
      get() = _builder.getPing()
      @JvmName("setPing")
      set(value) {
        _builder.setPing(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.PingCommand ping = 32;</code>
     */
    public fun clearPing() {
      _builder.clearPing()
    }
    /**
     * <code>.com.gamelift.rt.proto.PingCommand ping = 32;</code>
     * @return Whether the ping field is set.
     */
    public fun hasPing(): kotlin.Boolean {
      return _builder.hasPing()
    }

    /**
     * <code>.com.gamelift.rt.proto.PingResult pingResult = 33;</code>
     */
    public var pingResult: com.gamelift.rt.proto.CoreCommands.PingResult
      @JvmName("getPingResult")
      get() = _builder.getPingResult()
      @JvmName("setPingResult")
      set(value) {
        _builder.setPingResult(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.PingResult pingResult = 33;</code>
     */
    public fun clearPingResult() {
      _builder.clearPingResult()
    }
    /**
     * <code>.com.gamelift.rt.proto.PingResult pingResult = 33;</code>
     * @return Whether the pingResult field is set.
     */
    public fun hasPingResult(): kotlin.Boolean {
      return _builder.hasPingResult()
    }

    /**
     * <code>.com.gamelift.rt.proto.VerifyIdentityCommand verifyIdentity = 37;</code>
     */
    public var verifyIdentity: com.gamelift.rt.proto.CoreCommands.VerifyIdentityCommand
      @JvmName("getVerifyIdentity")
      get() = _builder.getVerifyIdentity()
      @JvmName("setVerifyIdentity")
      set(value) {
        _builder.setVerifyIdentity(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.VerifyIdentityCommand verifyIdentity = 37;</code>
     */
    public fun clearVerifyIdentity() {
      _builder.clearVerifyIdentity()
    }
    /**
     * <code>.com.gamelift.rt.proto.VerifyIdentityCommand verifyIdentity = 37;</code>
     * @return Whether the verifyIdentity field is set.
     */
    public fun hasVerifyIdentity(): kotlin.Boolean {
      return _builder.hasVerifyIdentity()
    }

    /**
     * <code>.com.gamelift.rt.proto.VerifyIdentityResult verifyIdentityResult = 38;</code>
     */
    public var verifyIdentityResult: com.gamelift.rt.proto.CoreCommands.VerifyIdentityResult
      @JvmName("getVerifyIdentityResult")
      get() = _builder.getVerifyIdentityResult()
      @JvmName("setVerifyIdentityResult")
      set(value) {
        _builder.setVerifyIdentityResult(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.VerifyIdentityResult verifyIdentityResult = 38;</code>
     */
    public fun clearVerifyIdentityResult() {
      _builder.clearVerifyIdentityResult()
    }
    /**
     * <code>.com.gamelift.rt.proto.VerifyIdentityResult verifyIdentityResult = 38;</code>
     * @return Whether the verifyIdentityResult field is set.
     */
    public fun hasVerifyIdentityResult(): kotlin.Boolean {
      return _builder.hasVerifyIdentityResult()
    }

    /**
     * <pre>
     * Connect Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.UDPConnectMessage udpConnect = 34;</code>
     */
    public var udpConnect: com.gamelift.rt.proto.CoreCommands.UDPConnectMessage
      @JvmName("getUdpConnect")
      get() = _builder.getUdpConnect()
      @JvmName("setUdpConnect")
      set(value) {
        _builder.setUdpConnect(value)
      }
    /**
     * <pre>
     * Connect Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.UDPConnectMessage udpConnect = 34;</code>
     */
    public fun clearUdpConnect() {
      _builder.clearUdpConnect()
    }
    /**
     * <pre>
     * Connect Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.UDPConnectMessage udpConnect = 34;</code>
     * @return Whether the udpConnect field is set.
     */
    public fun hasUdpConnect(): kotlin.Boolean {
      return _builder.hasUdpConnect()
    }

    /**
     * <code>.com.gamelift.rt.proto.PlayerConnectMessage playerConnect = 35;</code>
     */
    public var playerConnect: com.gamelift.rt.proto.CoreCommands.PlayerConnectMessage
      @JvmName("getPlayerConnect")
      get() = _builder.getPlayerConnect()
      @JvmName("setPlayerConnect")
      set(value) {
        _builder.setPlayerConnect(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.PlayerConnectMessage playerConnect = 35;</code>
     */
    public fun clearPlayerConnect() {
      _builder.clearPlayerConnect()
    }
    /**
     * <code>.com.gamelift.rt.proto.PlayerConnectMessage playerConnect = 35;</code>
     * @return Whether the playerConnect field is set.
     */
    public fun hasPlayerConnect(): kotlin.Boolean {
      return _builder.hasPlayerConnect()
    }

    /**
     * <code>.com.gamelift.rt.proto.PlayerDisconnectMessage playerDisconnect = 36;</code>
     */
    public var playerDisconnect: com.gamelift.rt.proto.CoreCommands.PlayerDisconnectMessage
      @JvmName("getPlayerDisconnect")
      get() = _builder.getPlayerDisconnect()
      @JvmName("setPlayerDisconnect")
      set(value) {
        _builder.setPlayerDisconnect(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.PlayerDisconnectMessage playerDisconnect = 36;</code>
     */
    public fun clearPlayerDisconnect() {
      _builder.clearPlayerDisconnect()
    }
    /**
     * <code>.com.gamelift.rt.proto.PlayerDisconnectMessage playerDisconnect = 36;</code>
     * @return Whether the playerDisconnect field is set.
     */
    public fun hasPlayerDisconnect(): kotlin.Boolean {
      return _builder.hasPlayerDisconnect()
    }

    /**
     * <pre>
     * Group Related Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.JoinGroup joinGroup = 40;</code>
     */
    public var joinGroup: com.gamelift.rt.proto.GroupMessages.JoinGroup
      @JvmName("getJoinGroup")
      get() = _builder.getJoinGroup()
      @JvmName("setJoinGroup")
      set(value) {
        _builder.setJoinGroup(value)
      }
    /**
     * <pre>
     * Group Related Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.JoinGroup joinGroup = 40;</code>
     */
    public fun clearJoinGroup() {
      _builder.clearJoinGroup()
    }
    /**
     * <pre>
     * Group Related Commands
     * </pre>
     *
     * <code>.com.gamelift.rt.proto.JoinGroup joinGroup = 40;</code>
     * @return Whether the joinGroup field is set.
     */
    public fun hasJoinGroup(): kotlin.Boolean {
      return _builder.hasJoinGroup()
    }

    /**
     * <code>.com.gamelift.rt.proto.LeaveGroup leaveGroup = 41;</code>
     */
    public var leaveGroup: com.gamelift.rt.proto.GroupMessages.LeaveGroup
      @JvmName("getLeaveGroup")
      get() = _builder.getLeaveGroup()
      @JvmName("setLeaveGroup")
      set(value) {
        _builder.setLeaveGroup(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.LeaveGroup leaveGroup = 41;</code>
     */
    public fun clearLeaveGroup() {
      _builder.clearLeaveGroup()
    }
    /**
     * <code>.com.gamelift.rt.proto.LeaveGroup leaveGroup = 41;</code>
     * @return Whether the leaveGroup field is set.
     */
    public fun hasLeaveGroup(): kotlin.Boolean {
      return _builder.hasLeaveGroup()
    }

    /**
     * <code>.com.gamelift.rt.proto.RequestGroupMembership requestGroupMembership = 42;</code>
     */
    public var requestGroupMembership: com.gamelift.rt.proto.GroupMessages.RequestGroupMembership
      @JvmName("getRequestGroupMembership")
      get() = _builder.getRequestGroupMembership()
      @JvmName("setRequestGroupMembership")
      set(value) {
        _builder.setRequestGroupMembership(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.RequestGroupMembership requestGroupMembership = 42;</code>
     */
    public fun clearRequestGroupMembership() {
      _builder.clearRequestGroupMembership()
    }
    /**
     * <code>.com.gamelift.rt.proto.RequestGroupMembership requestGroupMembership = 42;</code>
     * @return Whether the requestGroupMembership field is set.
     */
    public fun hasRequestGroupMembership(): kotlin.Boolean {
      return _builder.hasRequestGroupMembership()
    }

    /**
     * <code>.com.gamelift.rt.proto.GroupMembershipUpdate groupMembershipUpdate = 43;</code>
     */
    public var groupMembershipUpdate: com.gamelift.rt.proto.GroupMessages.GroupMembershipUpdate
      @JvmName("getGroupMembershipUpdate")
      get() = _builder.getGroupMembershipUpdate()
      @JvmName("setGroupMembershipUpdate")
      set(value) {
        _builder.setGroupMembershipUpdate(value)
      }
    /**
     * <code>.com.gamelift.rt.proto.GroupMembershipUpdate groupMembershipUpdate = 43;</code>
     */
    public fun clearGroupMembershipUpdate() {
      _builder.clearGroupMembershipUpdate()
    }
    /**
     * <code>.com.gamelift.rt.proto.GroupMembershipUpdate groupMembershipUpdate = 43;</code>
     * @return Whether the groupMembershipUpdate field is set.
     */
    public fun hasGroupMembershipUpdate(): kotlin.Boolean {
      return _builder.hasGroupMembershipUpdate()
    }
    public val messageCase: com.gamelift.rt.proto.PacketOuterClass.Packet.MessageCase
      @JvmName("getMessageCase")
      get() = _builder.getMessageCase()

    public fun clearMessage() {
      _builder.clearMessage()
    }
  }
}
@kotlin.jvm.JvmSynthetic
public inline fun com.gamelift.rt.proto.PacketOuterClass.Packet.copy(block: com.gamelift.rt.proto.PacketKt.Dsl.() -> kotlin.Unit): com.gamelift.rt.proto.PacketOuterClass.Packet =
  com.gamelift.rt.proto.PacketKt.Dsl._create(this.toBuilder()).apply { block() }._build()