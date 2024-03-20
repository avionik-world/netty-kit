package world.avionik.nettykit.client

import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise

/**
 * @author Niklas Nieberler
 */

class NettyClientHandler(
    private val nettyClientManager: NettyClientManager
) {

    /**
     * Sending a packet to the netty server
     * @param packet what you want to send
     * @param timeout of packet
     */
    fun sendPacketToServer(packet: IPacket, timeout: Long = 500): ICommunicationPromise<Unit> {
        return this.nettyClientManager.getConnection().sendUnitQuery(packet, timeout)
    }

    /**
     * Sending a packet to the netty server
     * @param packet what you want to send
     * @param timeout of packet
     */
    fun <T : Any> sendPacketToServer(
        packet: IPacket,
        expectedResponseClass: Class<T>,
        timeout: Long = 500
    ): ICommunicationPromise<T> {
        return this.nettyClientManager.getConnection().sendQuery(packet, expectedResponseClass, timeout)
    }

    /**
     * Returns true when this connection open is
     */
    fun isConnectedWithServer(): Boolean {
        return this.nettyClientManager.getConnection().isOpen()
    }

}

inline fun <reified T : Any> NettyClientHandler.sendResponsePacketToServer(
    packet: IPacket,
    timeout: Long = 500
): ICommunicationPromise<T> {
    return this.sendPacketToServer(packet, T::class.java, timeout)
}