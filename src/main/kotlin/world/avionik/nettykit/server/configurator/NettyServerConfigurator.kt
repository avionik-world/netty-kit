package world.avionik.nettykit.server.configurator

import eu.thesimplecloud.clientserverapi.lib.handler.IConnectionHandler
import eu.thesimplecloud.clientserverapi.lib.packet.IPacket
import world.avionik.nettykit.server.NettyServerHandler
import world.avionik.nettykit.server.NettyServerManager
import world.avionik.nettykit.server.connection.ConnectionHandler
import world.avionik.nettykit.server.packet.PacketInClientIsActive

/**
 * @author Niklas Nieberler
 */

class NettyServerConfigurator(
    private val configuration: NettyServerConfiguration
) {

    private var connectionHandler: IConnectionHandler = ConnectionHandler()
    private val packets = arrayListOf<Class<out IPacket>>(
        PacketInClientIsActive::class.java
    )

    fun withConnectionHandler(connectionHandler: IConnectionHandler): NettyServerConfigurator {
        this.connectionHandler = connectionHandler
        return this
    }

    fun withPackets(vararg packet: Class<out IPacket>): NettyServerConfigurator {
        this.packets.addAll(packet)
        return this
    }

    fun start(): NettyServerHandler {
        val nettyServerManager = NettyServerManager(this.packets, this.configuration, this.connectionHandler)
        nettyServerManager.initialize()
        return NettyServerHandler(nettyServerManager)
    }

}