package org.adolf.netty.firstexample

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
 * 启动服务端
 *
 * @author adolf
 * @date 2020/6/14
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
            .childHandler(TestServerInitializer())

        // 绑定到端口
        val channelFuture = serverBootstrap.bind(8899).sync()
        // 设置关闭的监听
        channelFuture.channel().closeFuture().sync()
    } finally {
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }
}