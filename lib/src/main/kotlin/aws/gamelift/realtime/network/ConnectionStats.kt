package aws.gamelift.realtime.network

// TODO use kotlin.native.concurrent.AtomicLong?
import java.util.concurrent.atomic.AtomicLong

class ConnectionStats {
	// NOTE: may add other stats (e.g. bytes) in the future
    private var messagesSent = AtomicLong(0)
    private var messagesReceived = AtomicLong(0)

    fun getCopy():ConnectionStats
    {
        val stats = ConnectionStats()
        stats.messagesSent = AtomicLong(messagesSent.get())
        stats.messagesReceived = AtomicLong(messagesReceived.get())

        return stats
    }

    fun reset()
    {
        messagesSent.set(0)
        messagesReceived.set(0)
    }

    fun recordMessageSent()
    {
        messagesSent.incrementAndGet()
    }

    fun recordMessageReceived()
    {
        messagesReceived.incrementAndGet()
    }

    fun getMessagesSent():Long
    {
        return messagesSent.get()
    }

    fun getMessagesReceived():Long
    {
        return messagesReceived.get()
    }
}