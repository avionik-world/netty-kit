package world.avionik.nettykit.client.config

import kotlinx.serialization.Serializable

/**
 * @author Niklas Nieberler
 */

@Serializable
class NettyClientConfig(
    val systemName: String,
    val host: String,
    val port: Int,
    val options: Map<String, String>
) {

    object Default {
        fun get(): NettyClientConfig {
            return NettyClientConfig(
                "System",
                "127.0.0.1",
                2000,
                emptyMap()
            )
        }
    }

}