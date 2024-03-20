package world.avionik.nettykit.server.config

import world.avionik.configkit.ConfigLoader
import world.avionik.configkit.format.YamlFileFormatter
import java.io.File

/**
 * @author Niklas Nieberler
 */

class NettyServerConfigLoader(
    path: String
) : ConfigLoader<NettyServerConfig>(
    File(path.ifBlank { "netty-server.yaml" }),
    YamlFileFormatter(
        NettyServerConfig.serializer()
    ),
    { NettyServerConfig.Default.get() }
)