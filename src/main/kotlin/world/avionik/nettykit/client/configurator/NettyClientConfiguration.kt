package world.avionik.nettykit.client.configurator

/**
 * @author Niklas Nieberler
 */

class NettyClientConfiguration(
    val systemName: String,
    val host: String,
    val port: Int,
    val options: Map<String, String> = emptyMap()
) {

    override fun toString(): String = "$host:$port"

}