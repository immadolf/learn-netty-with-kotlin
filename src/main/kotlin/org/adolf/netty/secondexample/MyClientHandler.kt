package org.adolf.netty.secondexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.time.LocalDateTime

class MyClientHandler : SimpleChannelInboundHandler<String>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        println(ctx.channel().remoteAddress())

        println("client output: $msg")
        ctx.channel().writeAndFlush("from client: ${LocalDateTime.now()}")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}