package world.avionik.nettykit

import eu.thesimplecloud.clientserverapi.server.client.connectedclient.IConnectedClient

/**
 * @author Niklas Nieberler
 */

fun IConnectedClient<ConnectedClientValue>.getOptions(): Map<String, String> {
    val clientValue = this.getClientValue() ?: return emptyMap()
    return clientValue.getOptions()
}

fun IConnectedClient<ConnectedClientValue>.getOption(key: String): String? {
    val clientValue = this.getClientValue() ?: return null
    return clientValue.getOption(key)
}

fun IConnectedClient<ConnectedClientValue>.hasOption(key: String): Boolean {
    val clientValue = this.getClientValue() ?: return false
    return clientValue.hasOption(key)
}