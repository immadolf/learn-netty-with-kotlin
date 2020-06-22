package org.adolf.netty.fourthexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.timeout.IdleState.*
import io.netty.handler.timeout.IdleStateEvent

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */
class MyServerHandler : ChannelInboundHandlerAdapter() {
    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        if (evt is IdleStateEvent) {
            val eventType = when (evt.state()) {
                READER_IDLE -> "读空闲"
                WRITER_IDLE -> "写空闲"
                ALL_IDLE -> "读写空闲"
                else -> throw IllegalStateException()
            }

            println("${ctx.channel().remoteAddress()} 超时事件： $eventType")
            ctx.channel().close()
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        println(msg)
        ctx.fireChannelRead(msg)
    }
}