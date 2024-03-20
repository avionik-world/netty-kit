package world.avionik.nettykit.client.configurator

import eu.thesimplecloud.clientserverapi.lib.handler.DefaultConnectionHandler
import eu.thesimplecloud.clientserverapi.lib.handler.IConnectionHandler
import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import world.avionik.nettykit.ConnectedClientValue
import world.avionik.nettykit.client.NettyClientHandler
import world.avionik.nettykit.client.NettyClientManager
import world.avionik.nettykit.client.logging.DefaultLoggingExecutor
import world.avionik.nettykit.client.logging.LoggingExecutor
import world.avionik.nettykit.client.logging.PrintLoggingExecutor
import world.avionik.nettykit.client.packet.PacketOutClientIsActive
import world.avionik.nettykit.client.reconnection.ReconnectConnectionHandler

/**
 * @author Niklas Nieberler
 */

class NettyClientConfigurator(
    private val configuration: NettyClientConfiguration,
    private val clientName: String
) {

    private val options = configuration.options.toMutableMap()
    private val packets = arrayListOf<Class<out IPacket>>(
        PacketOutClientIsActive::class.java
    )

    private var connectionHandlerFunction: (NettyClientManager) -> IConnectionHandler = { DefaultConnectionHandler() }
    private var loggingExecutor: LoggingExecutor = PrintLoggingExecutor(configuration.systemName)

    fun withPackets(vararg packet: Class<out IPacket>): NettyClientConfigurator {
        this.packets.addAll(packet)
        return this
    }

    fun withOption(key: String, value: String): NettyClientConfigurator {
        this.options[key] = value
        return this
    }

    fun withSystemLogging(): NettyClientConfigurator {
        withLoggingExecutor(DefaultLoggingExecutor(this.configuration.systemName))
        return this
    }

    fun withLoggingExecutor(loggingExecutor: LoggingExecutor): NettyClientConfigurator {
        this.loggingExecutor = loggingExecutor
        return this
    }

    fun withConnectionHandler(function: (NettyClientManager) -> IConnectionHandler): NettyClientConfigurator {
        this.connectionHandlerFunction = function
        return this
    }

    fun withAutoReconnect(reconnectionDelay: Long = 5): NettyClientConfigurator {
        withConnectionHandler { ReconnectConnectionHandler(it, reconnectionDelay) }
        return this
    }

    fun start(): NettyClientHandler {
        val connectedClientValue = ConnectedClientValue(this.clientName, this.options)
        val nettyClientManager = NettyClientManager(
            connectedClientValue,
            this.configuration,
            this.connectionHandlerFunction,
            this.packets,
            this.loggingExecutor,
        )
        val newNettyClient = nettyClientManager.createClient()
        nettyClientManager.initialize(newNettyClient)
        return NettyClientHandler(nettyClientManager)
    }

}