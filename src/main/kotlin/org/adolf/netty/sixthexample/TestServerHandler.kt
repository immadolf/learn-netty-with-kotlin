package org.adolf.netty.sixthexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.adolf.protobuf.StudentProtos

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */
class TestServerHandler : SimpleChannelInboundHandler<StudentProtos.Student>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: StudentProtos.Student) {
        println(msg.name)
        println(msg.age)
        println(msg.address)
    }

}