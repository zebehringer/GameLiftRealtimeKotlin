package aws.gamelift.realtime

abstract class ClientLogger {
    abstract fun debug(message:String, vararg args:Any)
    abstract fun info(message:String, vararg args:Any)
    abstract fun warn(message:String, vararg args:Any)
    abstract fun error(message:String, vararg args:Any)

    companion object {
        private const val LOG_FORMAT_STRING = "[%s] %s"
    }

    protected fun format(message:String, vararg args:Any):String
    {
        return if (args.isNotEmpty()) {
            String.format(message, args)
        } else {
            message
        }
    }
}