package world.avionik.nettykit.client.logging

import org.apache.logging.log4j.LogManager

/**
 * @author Niklas Nieberler
 */

class DefaultLoggingExecutor(
    private val systemName: String
) : LoggingExecutor {

    private val logger = LogManager.getLogger(getLoggerName())

    override fun info(message: String) {
        this.logger.info(message)
    }

    override fun warn(message: String) {
        this.logger.warn(message)
    }

    private fun getLoggerName(): String {
        if (this.systemName.isBlank())
            return "NETTY"
        return "${systemName.uppercase()} - NETTY"
    }

}