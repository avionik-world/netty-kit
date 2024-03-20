package world.avionik.nettykit.client.reconnection

import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.handler.IConnectionHandler
import world.avionik.nettykit.client.NettyClientManager

/**
 * @author Niklas Nieberler
 */

open class ReconnectConnectionHandler(
    private val nettyClientManager: NettyClientManager,
    private val reconnectionDelay: Long
) : IConnectionHandler {

    override fun onConnectionActive(connection: IConnection) {
        val reconnectManager = this.nettyClientManager.reconnectManager
        reconnectManager.stopClientReconnection()
    }

    override fun onConnectionInactive(connection: IConnection) {
        val reconnectManager = this.nettyClientManager.reconnectManager
        reconnectManager.reconnectNettyClient(this.reconnectionDelay)
    }

    override fun onFailure(connection: IConnection, ex: Throwable) {
        throw ex
    }
}