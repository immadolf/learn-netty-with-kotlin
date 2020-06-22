package org.adolf.netty.thirdexample

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import org.adolf.netty.secondexample.MyServerInitializer

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */

fun main(args: Array<String>) {
    //从客户端接收连接然后转给worker
    val bossGroup: EventLoopGroup = NioEventLoopGroup()
    //完成连接的处理
    val workerGroup: EventLoopGroup = NioEventLoopGroup()
    try {
        val serverBootstrap: ServerBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(MyChatServerInitializer())

        // 绑定到端口
        val channelFuture = serverBootstrap.bind(8899).sync() //sync表示一直等待
        // 设置关闭的监听
        channelFuture.channel().closeFuture().sync()
    } finally {
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}