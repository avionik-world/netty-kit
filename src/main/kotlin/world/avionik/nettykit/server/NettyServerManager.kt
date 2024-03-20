package world.avionik.nettykit.server

import eu.thesimplecloud.clientserverapi.lib.handler.DefaultServerHandler
import eu.thesimplecloud.clientserverapi.lib.handler.IConnectionHandler
import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import eu.thesimplecloud.clientserverapi.server.NettyServer
import eu.thesimplecloud.clientserverapi.server.client.clientmanager.IClientManager
import org.apache.logging.log4j.LogManager
import world.avionik.nettykit.ConnectedClientValue
import world.avionik.nettykit.server.configurator.NettyServerConfiguration

/**
 * @author Niklas Nieberler
 */

class NettyServerManager(
    private val packets: List<Class<out IPacket>>,
    private val configuration: NettyServerConfiguration,
    connectionHandler: IConnectionHandler
) {

    private val nettyServer = NettyServer(
        configuration.host,
        configuration.port,
        connectionHandler,
        DefaultServerHandler<ConnectedClientValue>()
    )

    private val logger = LogManager.getLogger("NETTY")

    /**
     * Initialize and start a netty server
     */
    fun initialize() {
        this.logger.info("NettyServer is currently being initialised...")
        this.logger.info("Following packages will be registered:")
        this.packets.forEach {
            this.nettyServer.getPacketManager().registerPacket(it)
            this.logger.info("> ${it.simpleName}")
        }

        this.logger.info("NettyServer is now started on $configuration")
        this.nettyServer.start()
            .awaitUninterruptibly()
            .throwFailure()
    }

    /**
     * Stopps the netty server
     */
    fun shutdown() {
        this.logger.info("NettyServer is shutting down")
        this.nettyServer.shutdown().awaitUninterruptibly()
    }

    /**
     * Gets the [IClientManager] of the netty server
     */
    fun getClientManager(): IClientManager<ConnectedClientValue> {
        return this.nettyServer.getClientManager()
    }

}