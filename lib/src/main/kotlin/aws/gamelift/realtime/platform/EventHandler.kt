package aws.gamelift.realtime.platform

class EventHandler<T : Any> : ArrayList<(Any, T) -> Unit>() {
	fun invoke(sender:Any, args:T) {
		for (listener in this) {
			listener(sender, args)
		}
	}
}
