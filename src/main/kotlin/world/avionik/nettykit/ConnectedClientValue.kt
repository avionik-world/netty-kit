package world.avionik.nettykit

import eu.thesimplecloud.clientserverapi.server.client.connectedclient.IConnectedClientValue
import java.lang.StringBuilder

/**
 * @author Niklas Nieberler
 */

class ConnectedClientValue(
    val clientName: String,
    private val options: Map<String, String>
) : IConnectedClientValue {

    /**
     * Gets all options of this hashmap
     */
    fun getOptions(): Map<String, String> = this.options

    /**
     * Gets the value of this option
     * @param key the option key
     * @return value of key
     */
    fun getOption(key: String): String? {
        return this.options[key]
    }

    /**
     * Returns true if the map contains the specified key.
     * @param key the option key
     */
    fun hasOption(key: String): Boolean {
        return this.options.containsKey(key)
    }

    override fun toString(): String {
        return this.clientName + toOptionString()
    }

    private fun toOptionString(): String {
        if (this.options.isEmpty())
            return ""
        val stringBuilder = StringBuilder()
        this.options.forEach { (key, value) -> stringBuilder.append("$key=$value, ") }
        return " (${stringBuilder.toString().dropLast(2)})"
    }

}