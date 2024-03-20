package world.avionik.nettykit.server.configurator

import world.avionik.nettykit.server.config.NettyServerConfigLoader

/**
 * @author Niklas Nieberler
 */

object NettyServerSettings {

    /**
     * Gets the netty configuration from a system environment
     * @param host the environment to create the configuration
     * @param port the environment to create the configuration
     * @return configuration from the environment
     */
    fun fromEnv(host: String = "NETTY_SERVER_HOST", port: String = "NETTY_SERVER_PORT"): NettyServerConfiguration {
        return NettyServerConfiguration(
            System.getenv(host),
            System.getenv(port).toInt()
        )
    }

    /**
     * Gets the netty configuration from the config
     * @param path directory path of the config
     * @return configuration from the config
     */
    fun fromConfig(path: String = ""): NettyServerConfiguration {
        val nettyServerConfig = NettyServerConfigLoader(path).load()
        return NettyServerConfiguration(
            nettyServerConfig.host,
            nettyServerConfig.port
        )
    }

}