package org.adolf.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel
import java.time.LocalDateTime

fun main() {
    val socketChannel = SocketChannel.open()
    socketChannel.configureBlocking(false)

    val selector = Selector.open()
    //位或运算，同时监听两种事件
    socketChannel.register(selector, SelectionKey.OP_CONNECT or SelectionKey.OP_READ)
    socketChannel.connect(InetSocketAddress("127.0.0.1", 8899))

    while (true) {
        selector.select()
        val selectedKeys = selector.selectedKeys()
        val iterator = selectedKeys.iterator()
        while (iterator.hasNext()) {
            val selectionKey = iterator.next()
            if (selectionKey.isConnectable) {
                val clientChannel = selectionKey.channel() as SocketChannel
                if (clientChannel.isConnectionPending) {
                    clientChannel.finishConnect()
                    val buffer = ByteBuffer.allocate(1024)
                    buffer.put("${LocalDateTime.now()},连接成功".toByteArray())
                    buffer.flip()
                    clientChannel.write(buffer)

                    Thread {
                        while (true) {
                            buffer.clear()
                            val msg = readLine()
                            msg?.run {
                                buffer.put(msg.toByteArray())
                                buffer.flip()
                                clientChannel.write(buffer)
                            }
                        }
                    }.start()
                }
                iterator.remove()
            } else if (selectionKey.isReadable) {
                val clientChannel = selectionKey.channel() as SocketChannel
                val buffer = ByteBuffer.allocate(1024)
                val count = clientChannel.read(buffer)
                if (count > 0) {
                    println("来自服务端的消息：${String(buffer.array(), 0, count)}")
                }
                iterator.remove()
            }
        }
    }
}