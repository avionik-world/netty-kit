package world.avionik.nettykit.server.config

import kotlinx.serialization.Serializable

/**
 * @author Niklas Nieberler
 */

@Serializable
class NettyServerConfig(
    val host: String,
    val port: Int
) {

    object Default {
        fun get(): NettyServerConfig {
            return NettyServerConfig(
                "127.0.0.1",
                2000
            )
        }
    }

}