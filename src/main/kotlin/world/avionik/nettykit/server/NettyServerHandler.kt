package world.avionik.nettykit.server

import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import eu.thesimplecloud.clientserverapi.lib.promise.CommunicationPromise
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise
import eu.thesimplecloud.clientserverapi.server.client.connectedclient.IConnectedClient
import world.avionik.nettykit.ConnectedClientValue

/**
 * @author Niklas Nieberler
 */

class NettyServerHandler(
    private val nettyServerManager: NettyServerManager
) {

    /**
     * Sending [IPacket] to a netty client
     * @param packet what you want to send
     * @param name of netty client
     * @param timeout of packet
     * @return communication promise of this packet
     */
    fun sendPacket(packet: IPacket, name: String, timeout: Long = 300): ICommunicationPromise<Unit> {
        return getClient(name)?.sendUnitQuery(packet, timeout)
            ?: CommunicationPromise.failed(NullPointerException("client $name is null"))
    }

    /**
     * Sending [IPacket] to a netty client
     * @param packet what you want to send
     * @param name of netty client
     * @param timeout of packet
     * @return communication promise of this packet
     */
    fun <T : Any> sendPacket(
        packet: IPacket,
        name: String,
        expectedResponseClass: Class<T>,
        timeout: Long = 300
    ): ICommunicationPromise<T> {
        return getClient(name)?.sendQuery(packet, expectedResponseClass, timeout)
            ?: CommunicationPromise.failed(NullPointerException("client $name is null"))
    }

    /**
     * Sending [IPacket] to all netty clients
     * @param packet what you want to send
     * @param timeout of packet
     */
    fun sendPacketToAllClients(packet: IPacket, timeout: Long = 500) {
        getClients().forEach { it.sendUnitQuery(packet, timeout) }
    }

    /**
     * Gets [ConnectedClientValue] of the netty client
     * @param name of the netty client name
     */
    fun getClient(name: String): IConnectedClient<ConnectedClientValue>? {
        return this.nettyServerManager.getClientManager().getClients()
            .firstOrNull { it.getClientValue()?.clientName == name }
    }

    /**
     * Gets list of all netty clients
     * @return collection of [IConnectedClient<ConnectedClientValue>]
     */
    fun getClients(): Collection<IConnectedClient<ConnectedClientValue>> {
        return this.nettyServerManager.getClientManager().getClients()
    }

    /**
     * Stopps the netty server
     */
    fun shutdown() {
        this.nettyServerManager.shutdown()
    }

}

/**
 * Sending [IPacket] to a netty client
 * @param packet what you want to send
 * @param name of netty client
 * @param timeout of packet
 */
inline fun <reified T : Any> NettyServerHandler.sendResponsePacket(
    packet: IPacket,
    name: String,
    timeout: Long = 500
): ICommunicationPromise<T> {
    return this.sendPacket(packet, name, T::class.java, timeout)
}