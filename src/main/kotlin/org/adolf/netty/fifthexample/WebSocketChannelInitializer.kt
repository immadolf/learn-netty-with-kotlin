package org.adolf.netty.fifthexample

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.stream.ChunkedWriteHandler

/**
 *
 *
 * @author adolf
 * @date 2020/6/22
 * @since
 */
class WebSocketChannelInitializer : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()
        //websocket 基于 http协议，所以需要有http协议的编解码器
        pipeline.addLast(HttpServerCodec())
        //以块为单位写数据
        pipeline.addLast(ChunkedWriteHandler())
        //聚合http request/response
        pipeline.addLast(HttpObjectAggregator(8192))
        pipeline.addLast(WebSocketServerProtocolHandler("/ws"))

        TODO("pipeline.addLast(null)")
    }
}