package org.adolf.netty.fourthexample

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.timeout.IdleStateHandler
import java.util.concurrent.TimeUnit

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */
class MyServerInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()
        pipeline.addLast(IdleStateHandler(5, 7, 10, TimeUnit.SECONDS))
        pipeline.addLast(MyServerHandler())
    }

}