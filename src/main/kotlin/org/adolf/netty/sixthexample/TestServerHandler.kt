package org.adolf.netty.sixthexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.adolf.protobuf.StudentProtos
import java.lang.IllegalArgumentException

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */
class TestServerHandler : SimpleChannelInboundHandler<StudentProtos.MyMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: StudentProtos.MyMessage) {

        when (msg.dataType) {
            StudentProtos.MyMessage.DataType.DogType -> {
                val dog = msg.dog
                println(dog.name)
                println(dog.age)
            }
            StudentProtos.MyMessage.DataType.PersonType -> {
                val student = msg.student
                println(student.name)
                println(student.age)
                println(student.address)
            }
            StudentProtos.MyMessage.DataType.CatType -> {
                val cat = msg.cat
                println(cat.name)
                println(cat.city)
            }
            else -> throw IllegalArgumentException()
        }

    }

}