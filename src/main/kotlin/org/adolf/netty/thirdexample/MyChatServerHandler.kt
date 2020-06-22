package org.adolf.netty.thirdexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */
class MyChatServerHandler : SimpleChannelInboundHandler<String>() {

    companion object {
        // 这里必须是静态的，因为MyChatServerHandler不是单例，每个客户端进来都是有不同的实例，这样不同的客户端就无法共享channelGroup
        private val channelGroup = DefaultChannelGroup(GlobalEventExecutor.INSTANCE)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        val sender = ctx.channel()

        channelGroup.forEach { ch ->
            if (ch != sender) {
                //\r\n不能省略
                ch.writeAndFlush("${sender.remoteAddress()} 发送的消息: $msg\r\n")
            } else {
                //\r\n不能省略
                sender.writeAndFlush("[自己]: $msg\r\n")
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    //表示连接处于活动状态
    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        println("${channel.remoteAddress()} 上线")
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        println("${channel.remoteAddress()} 下线")
    }

    override fun handlerAdded(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()

        //广播给组中的所有客户端
        //\r\n不能省略
        channelGroup.writeAndFlush("[服务器] - ${channel.remoteAddress()} 加入\r\n")

        channelGroup.add(channel)
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        //\r\n不能省略
        channelGroup.writeAndFlush("[服务器] - ${channel.remoteAddress()} 离开\r\n")

        //channelGroup.remove(channel) netty会自动调用，是否手动调用都无影响
    }
}