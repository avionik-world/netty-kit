package world.avionik.nettykit.client.logging

/**
 * @author Niklas Nieberler
 */

class PrintLoggingExecutor(
    private val systemName: String
) : LoggingExecutor {

    override fun info(message: String) {
        println("[${getLoggerName()}] $message")
    }

    override fun warn(message: String) {
        info(message)
    }

    private fun getLoggerName(): String {
        if (this.systemName.isBlank())
            return "NETTY"
        return "${systemName.uppercase()} - NETTY"
    }
}