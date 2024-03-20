package world.avionik.nettykit.server.configurator

/**
 * @author Niklas Nieberler
 */

class NettyServerConfiguration(
    val host: String,
    val port: Int
) {

    override fun toString(): String = "$host:$port"

}