package world.avionik.nettykit.client.logging

/**
 * @author Niklas Nieberler
 */

interface LoggingExecutor {

    /**
     * Sends an information message
     * @param message to send
     */
    fun info(message: String)

    /**
     * Sends a warning message
     * @param message to send
     */
    fun warn(message: String)

}