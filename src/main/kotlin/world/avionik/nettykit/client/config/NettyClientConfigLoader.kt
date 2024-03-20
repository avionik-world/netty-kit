package world.avionik.nettykit.client.config

import world.avionik.configkit.ConfigLoader
import world.avionik.configkit.format.YamlFileFormatter
import java.io.File

/**
 * @author Niklas Nieberler
 */

class NettyClientConfigLoader(
    path: String
) : ConfigLoader<NettyClientConfig>(
    File(path),
    YamlFileFormatter(
        NettyClientConfig.serializer()
    ),
    { NettyClientConfig.Default.get() }
)