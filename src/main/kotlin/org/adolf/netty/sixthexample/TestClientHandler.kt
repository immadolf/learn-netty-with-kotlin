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
class TestClientHandler : SimpleChannelInboundHandler<StudentProtos.Student>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: StudentProtos.Student) {

    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        val student = StudentProtos.Student
            .newBuilder()
            .setName("张三")
            .setAge(20)
            .setAddress("北京")
            .build()

        ctx.writeAndFlush(student)

    }
}