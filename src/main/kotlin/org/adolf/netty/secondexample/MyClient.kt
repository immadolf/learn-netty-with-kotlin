package org.adolf.netty.secondexample

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

fun main(args: Array<String>) {
    val eventLoopGroup = NioEventLoopGroup()

    try {
        val bootstrap = Bootstrap()
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel::class.java).handler(MyClientInitializer())

        val channelFuture = bootstrap.connect("localhost", 8899).sync()
        channelFuture.channel().closeFuture().sync()
    } catch (ex: Exception) {
        eventLoopGroup.shutdownGracefully()
    }

}