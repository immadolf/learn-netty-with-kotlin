package org.adolf.netty.fifthexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import java.time.LocalDateTime

//SimpleChannelInboundHandler的泛型表示要处理的对象类型
class TextWebSocketFrameHandler : SimpleChannelInboundHandler<TextWebSocketFrame>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: TextWebSocketFrame) {
        println("收到消息： ${msg.text()}")

        // 注意这里返回的必须是TextWebSocketFrame类型
        ctx.channel().writeAndFlush(TextWebSocketFrame("服务器时间： ${LocalDateTime.now()}"))
    }

    override fun handlerAdded(ctx: ChannelHandlerContext) {
        println("handler added: ${ctx.channel().id().asLongText()}")
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        println("handler removed: ${ctx.channel().id().asLongText()}")
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        println("发生异常")
        cause.printStackTrace()
        ctx.close()
    }
}