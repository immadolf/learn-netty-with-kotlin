package org.adolf.netty.sixthexample

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */
fun main() {
    val eventLoopGroup = NioEventLoopGroup()

    try {
        val bootstrap = Bootstrap()
        bootstrap.group(eventLoopGroup)
            .channel(NioSocketChannel::class.java)
            .handler(TestClientInitizlizer())

        val channelFuture = bootstrap.connect("localhost", 8899).sync()
        channelFuture.channel().closeFuture().sync()

    } catch (ex: Exception) {
        eventLoopGroup.shutdownGracefully()
    }

}