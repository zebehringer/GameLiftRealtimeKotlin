package aws.gamelift.realtime.platform

class NullableArgsEventHandler<T : EventArgs> : ArrayList<(Any, T?) -> Unit>() {
	fun invoke(sender:Any, args:T?) {
		for (listener in this) {
			listener(sender, args)
		}
	}
}
