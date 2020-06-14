package org.adolf.netty.firstexample

import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpServerCodec

/**
 *
 *
 * @author adolf
 * @date 2020/6/14
 * @since
 */
class TestServerInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel?) {
        val pipeline = ch!!.pipeline()

        // name 可选，netty会自动生成一个
        // http请求编解码器
        pipeline.addLast("httpServerCodec", HttpServerCodec())
        pipeline.addLast("testHttpServerHandler", TestHttpServerHandler())
    }
}