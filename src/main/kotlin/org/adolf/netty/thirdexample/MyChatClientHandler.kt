package org.adolf.netty.thirdexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */
class MyChatClientHandler : SimpleChannelInboundHandler<String>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        println(msg)
    }
}