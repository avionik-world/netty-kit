package world.avionik.nettykit.client.packet

import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.packet.packettype.ObjectPacket
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise
import world.avionik.nettykit.ConnectedClientValue

/**
 * @author Niklas Nieberler
 */

class PacketOutClientIsActive(
    connectedClientValue: ConnectedClientValue
) : ObjectPacket<ConnectedClientValue>(
    connectedClientValue
) {
    override suspend fun handle(connection: IConnection): ICommunicationPromise<Any> {
        return unit()
    }
}