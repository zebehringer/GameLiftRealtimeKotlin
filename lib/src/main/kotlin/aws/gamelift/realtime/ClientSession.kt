package aws.gamelift.realtime

open class ClientSession(var token:ConnectionToken? = null, var loggedIn:Boolean = false, var state:SessionState = SessionState.READY) {
	enum class SessionState
    {
        READY,          // Client is ready to connect
        OPEN,           // Client has opened a session but is not logged in
        CONNECTED,      // Client has logged in and is ready for messages
        DISCONNECTED,   // Client has disconnected
    }

    /// <summary>
    /// Represents the GameLift Realtime peer Id for client. Set on connection.
    /// </summary>
    open var connectedPeerId:Int = 0
		internal set
    private val groupMembership:HashSet<Int> = HashSet()

    open fun joinGroup(groupId:Int)
    {
        groupMembership.add(groupId)
    }

    open fun leaveGroup(groupId:Int)
    {
        groupMembership.remove(groupId)
    }

    fun updateGroupMembership(groupId:Int, players:IntArray?)
    {
        if (players != null && players.any{it == connectedPeerId})
        {
            joinGroup(groupId)
        }
        else
        {
            leaveGroup(groupId)
        }
    }

    fun isInGroup(groupId:Int):Boolean
    {
        return groupMembership.contains(groupId)
    }
}