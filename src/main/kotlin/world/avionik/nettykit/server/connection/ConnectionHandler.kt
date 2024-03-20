package world.avionik.nettykit.server.connection

import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.handler.IConnectionHandler
import eu.thesimplecloud.clientserverapi.server.client.connectedclient.IConnectedClient
import org.apache.logging.log4j.LogManager
import world.avionik.nettykit.ConnectedClientValue

/**
 * @author Niklas Nieberler
 */

@Suppress("UNCHECKED_CAST")
open class ConnectionHandler : IConnectionHandler {

    private val logger = LogManager.getLogger("NETTY")

    override fun onConnectionActive(connection: IConnection) {}

    override fun onConnectionInactive(connection: IConnection) {
        if (connection !is IConnectedClient<*>)
            return
        val clientValue = connection as IConnectedClient<ConnectedClientValue>
        this.logger.info("Client ${clientValue.getClientValue()?.clientName} has lost connection")
    }

    override fun onFailure(connection: IConnection, ex: Throwable) {
        throw ex
    }
}