package org.adolf.netty.secondexample

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder

class MyServerInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()

        pipeline.addLast(LengthFieldBasedFrameDecoder(Int.MAX_VALUE, 0, 4, 0, 4))
        pipeline.addLast(LengthFieldPrepender(4))
        pipeline.addLast(StringDecoder(Charsets.UTF_8))
        pipeline.addLast(StringEncoder(Charsets.UTF_8))
        pipeline.addLast(MyServerHandler())
    }
}