package world.avionik.nettykit.client.reconnection

import world.avionik.nettykit.client.NettyClientManager
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * @author Niklas Nieberler
 */

class NettyClientReconnectManager(
    private val nettyClientManager: NettyClientManager
) {

    private val scheduler = Executors.newScheduledThreadPool(1)

    private var scheduledReconnectFuture: ScheduledFuture<*>? = null

    fun reconnectNettyClient(delay: Long = 5) {
        this.scheduledReconnectFuture = scheduler.scheduleWithFixedDelay({
            this.nettyClientManager.loggingExecutor.info("Try to reconnect client to netty server (${nettyClientManager.configuration})...")
            val newNettyClient = this.nettyClientManager.createClient()
            this.nettyClientManager.initialize(newNettyClient)
        }, 5, delay, TimeUnit.SECONDS)
    }

    fun stopClientReconnection() {
        this.scheduledReconnectFuture?.cancel(false)
    }

}