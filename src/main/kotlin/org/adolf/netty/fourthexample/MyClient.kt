package org.adolf.netty.fourthexample

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import org.adolf.netty.thirdexample.MyChatClientInitializer

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */

fun main() {
    val eventLoopGroup = NioEventLoopGroup()

    try {
        val bootstrap = Bootstrap()
        bootstrap.group(eventLoopGroup)
            .channel(NioSocketChannel::class.java)
            .handler(MyChatClientInitializer())

        val channel = bootstrap.connect("localhost", 8899).sync().channel()


        while (true) {
            val msg = readLine().orEmpty()
            //\r\n不能省略
            channel.writeAndFlush("$msg\r\n")

        }

    } catch (ex: Exception) {
        eventLoopGroup.shutdownGracefully()
    }

}