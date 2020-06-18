package org.adolf.netty.firstexample

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.*
import io.netty.util.CharsetUtil
import java.net.URI

/**
 * 自定义的处理器
 *
 * @author adolf
 * @date 2020/6/14
 * @since
 */
class TestHttpServerHandler : SimpleChannelInboundHandler<HttpObject>() {

    // 读取客户端发过来的请求，并向客户端返回响应
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: HttpObject?) {

        println(msg!!.javaClass)

        println(ctx!!.channel().remoteAddress())
        if (msg is HttpRequest) {

            println("请求方法名：${msg.method().name()}")

            val uri = URI(msg.uri())
            if ("/favicon.ico".equals(uri.path)) {
                println("请求favicon.ico")
                return
            }

            // 返回给客户端的内容
            val content: ByteBuf = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8)

            // 构造HTTP响应
            val response: FullHttpResponse = DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content)

            // 设置HTTP响应头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes())

            // 向客户端返回响应
            ctx!!.writeAndFlush(response)
            ctx.channel().close()
        }
    }


    override fun channelActive(ctx: ChannelHandlerContext?) {
        println("channel active")
        super.channelActive(ctx)
    }

    override fun channelRegistered(ctx: ChannelHandlerContext?) {
        println("channel registered")
        super.channelRegistered(ctx)
    }

    override fun handlerAdded(ctx: ChannelHandlerContext?) {
        println("handler added")
        super.handlerAdded(ctx)
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        println("channel inactive")
        super.channelInactive(ctx)
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext?) {
        println("channel unregistered")
        super.channelUnregistered(ctx)
    }
}