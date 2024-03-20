# Netty Kit 🛩️
With the NettyKit it is easy to create a NettyServer and a NettyClient. It is important to note that this project requires the client server API.

## Using the Netty Kit in your plugin

### Maven
```xml
<dependencies>
 <dependency>
    <groupId>world.avionik</groupId>
    <artifactId>netty-kit</artifactId>
    <version>1.0.1</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

### Gradle
```groovy
dependencies {
    compileOnly 'world.avionik:netty-kit:1.0.1'
}
```

## How to create a Netty Server
You can use these methods to create a new NettyServer. There are two ways to specify the host and port. Once in a config or as a system environment variable 

``` kotlin
NettyServerConfigurator(NettyServerSettings.fromConfig())  // Here you can also use fromEnv() here
    .withPackets(PacketOutTesting::class.java, PacketInSomeCoolThings::class.java)
    .start()
```

Now you have the [NettyServerHandler](https://github.com/avionik-world/netty-kit/blob/master/src/main/kotlin/world/avionik/nettykit/server/NettyServerHandler.kt). You can then use this handler to send packets to the clients. Here is a small example:
``` kotlin
val nettyServerHandler = NettyServerConfigurator(NettyServerSettings.fromConfig())
    .withPackets(PacketOutTesting::class.java, PacketInSomeCoolThings::class.java)
    .start()

val packet = PacketOutTesting()

nettyServerHandler.sendPacket(packet, "netty-client") // Here you get a CommunicationPromise as a return type
    .addResultListener { println("success") }
    .addFailureListener { println("failed") }

// And this is how you send a packet to all connected Netty clients
nettyServerHandler.sendPacketToAllClients(packet)
```

## How to create a Netty Client
If you have already created the NettyServer, you now need the client. Here is how to create a Netty client:

``` kotlin
// And here, too, there are two options for the host and port settings.
NettyClientConfigurator(NettyClientSettings.fromConfig("netty-client.yaml"), "ClientName") // This is the name of the Netty client
    .withPackets(PacketInTesting::class.java, PacketOutSomeCoolThings::class.java)
    .withAutoReconnect() // This allows you to set the client to automatically reconnect in the event of a netty server shutdown
    .start()
```

Here you have the option of sending a packet to the Netty server with the [NettyClientHandler](https://github.com/avionik-world/netty-kit/blob/master/src/main/kotlin/world/avionik/nettykit/client/NettyClientHandler.kt):
``` kotlin
val nettyClientHandler = NettyClientConfigurator(NettyClientSettings.fromConfig("netty-client.yaml"), "ClientName")
    .withPackets(PacketInTesting::class.java, PacketOutSomeCoolThings::class.java)
    .withAutoReconnect()
    .start()

val packet = PacketOutSomeCoolThings()
nettyClientHandler.sendPacketToServer(packet)
    .addResultListener { println("success") }
    .addFailureListener { println("failed") }
```
