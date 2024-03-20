package world.avionik.nettykit.client.configurator

import world.avionik.nettykit.client.config.NettyClientConfigLoader

/**
 * @author Niklas Nieberler
 */

object NettyClientSettings {

    /**
     * Gets the netty configuration from a system environment
     * @param systemName the environment to create the configuration
     * @param host the environment to create the configuration
     * @param port the environment to create the configuration
     * @return configuration from the environment
     */
    fun fromEnv(
        systemName: String = "NETTY_CLIENT_SYSTEM_NAME",
        host: String = "NETTY_CLIENT_HOST",
        port: String = "NETTY_CLIENT_PORT"
    ): NettyClientConfiguration {
        return NettyClientConfiguration(
            System.getenv(systemName),
            System.getenv(host),
            System.getenv(port).toInt()
        )
    }

    /**
     * Gets the netty configuration from the config
     * @param path directory path of the config
     * @return configuration from the config
     */
    fun fromConfig(path: String): NettyClientConfiguration {
        val nettyServerConfig = NettyClientConfigLoader(path).load()
        return NettyClientConfiguration(
            nettyServerConfig.systemName,
            nettyServerConfig.host,
            nettyServerConfig.port,
            nettyServerConfig.options
        )
    }

}