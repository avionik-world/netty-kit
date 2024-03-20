package world.avionik.nettykit.client

import eu.thesimplecloud.clientserverapi.client.NettyClient
import eu.thesimplecloud.clientserverapi.lib.connection.IConnection
import eu.thesimplecloud.clientserverapi.lib.handler.IConnectionHandler
import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import world.avionik.nettykit.ConnectedClientValue
import world.avionik.nettykit.client.configurator.NettyClientConfiguration
import world.avionik.nettykit.client.logging.LoggingExecutor
import world.avionik.nettykit.client.packet.PacketOutClientIsActive
import world.avionik.nettykit.client.reconnection.NettyClientReconnectManager

/**
 * @author Niklas Nieberler
 */

class NettyClientManager(
    private val connectedClientValue: ConnectedClientValue,
    val configuration: NettyClientConfiguration,
    private val connectionHandlerFunction: (NettyClientManager) -> IConnectionHandler,
    private val packets: List<Class<out IPacket>>,
    val loggingExecutor: LoggingExecutor
) {

    val reconnectManager = NettyClientReconnectManager(this)

    private var nettyClient: NettyClient? = null

    /**
     * Creates a new netty client
     */
    fun createClient(): NettyClient {
        return NettyClient(
            this.configuration.host,
            this.configuration.port,
            this.connectionHandlerFunction(this)
        )
    }

    /**
     * Initialize a new netty client
     * @param nettyClient to initialize and start
     */
    fun initialize(nettyClient: NettyClient) {
        this.nettyClient = nettyClient

        this.loggingExecutor.info("NettyClient is currently being initialised...")
        this.loggingExecutor.info("Following packages will be registered:")
        this.packets.forEach {
            nettyClient.getPacketManager().registerPacket(it)
            this.loggingExecutor.info("> ${it.simpleName}")
        }

        this.loggingExecutor.info("NettyClient is now started on $configuration")
        nettyClient.start()
            .awaitUninterruptibly()
            .throwFailure()

        try {
            Thread.sleep(200)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val packet = PacketOutClientIsActive(this.connectedClientValue)
        nettyClient.getConnection().sendUnitQuery(packet)
    }

    /**
     * Stopps the netty client
     */
    fun shutdown() {
        this.nettyClient?.shutdown()?.awaitUninterruptibly()
            ?: throw IllegalStateException("No netty client has been created yet")
    }

    fun getConnection(): IConnection {
        return this.nettyClient?.getConnection()
            ?: throw IllegalStateException("No netty client has been created yet")
    }

}