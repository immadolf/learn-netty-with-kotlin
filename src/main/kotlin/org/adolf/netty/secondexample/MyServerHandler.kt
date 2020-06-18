package org.adolf.netty.secondexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.util.*

class MyServerHandler : SimpleChannelInboundHandler<String>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        println("${ctx.channel().remoteAddress()}, $msg")
        ctx.channel().writeAndFlush("from server: ${UUID.randomUUID()}")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}