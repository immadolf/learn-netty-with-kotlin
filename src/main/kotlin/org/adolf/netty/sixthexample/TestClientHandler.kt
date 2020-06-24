package org.adolf.netty.sixthexample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.adolf.protobuf.StudentProtos
import kotlin.random.Random

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */
class TestClientHandler : SimpleChannelInboundHandler<StudentProtos.MyMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: StudentProtos.MyMessage) {

    }

    override fun channelActive(ctx: ChannelHandlerContext) {

        val message = when (Random.Default.nextInt(3)) {
            0 -> {
                val student = StudentProtos.Student
                    .newBuilder().setName("张三").setAge(20).setAddress("北京").build()
                StudentProtos.MyMessage.newBuilder()
                    .setDataType(StudentProtos.MyMessage.DataType.PersonType)
                    .setStudent(student)
                    .build()
            }
            1 -> {
                val dog = StudentProtos.Dog.newBuilder()
                    .setName("狗").setAge(3).build()
                StudentProtos.MyMessage.newBuilder()
                    .setDataType(StudentProtos.MyMessage.DataType.DogType)
                    .setDog(dog)
                    .build()
            }
            else -> {
                val cat = StudentProtos.Cat.newBuilder()
                    .setName("猫").setCity("伊斯坦布尔").build()
                StudentProtos.MyMessage.newBuilder()
                    .setDataType(StudentProtos.MyMessage.DataType.CatType)
                    .setCat(cat)
                    .build()
            }
        }

        ctx.writeAndFlush(message)

    }
}