package world.avionik.nettykit.server.packet

import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.packet.packettype.ObjectPacket
import eu.thesimplecloud.clientserverapi.lib.promise.ICommunicationPromise
import eu.thesimplecloud.clientserverapi.server.client.connectedclient.IConnectedClient
import org.apache.logging.log4j.LogManager
import world.avionik.nettykit.ConnectedClientValue

/**
 * @author Niklas Nieberler
 */

@Suppress("UNCHECKED_CAST")
class PacketInClientIsActive : ObjectPacket<ConnectedClientValue>() {

    private val logger = LogManager.getLogger("NETTY")

    override suspend fun handle(connection: IConnection): ICommunicationPromise<Any> {
        val value = value ?: return contentException("value")
        val clientValue = connection as IConnectedClient<ConnectedClientValue>
        clientValue.setClientValue(value)
        this.logger.info("Client $value is now active")
        return success("")
    }

}