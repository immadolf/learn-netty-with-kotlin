package org.adolf.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

fun main() {
    val serverSocketChannel = ServerSocketChannel.open()
    serverSocketChannel.configureBlocking(false)
    val serverSocket = serverSocketChannel.socket()
    serverSocket.bind(InetSocketAddress(8899))

    val selector = Selector.open()
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)

    val clientMap = mutableMapOf<String, SocketChannel>()

    while (true) {
        selector.select()
        val selectedKeys = selector.selectedKeys()
        val iterator = selectedKeys.iterator()
        while (iterator.hasNext()) {
            val selectionKey = iterator.next()
            when {
                selectionKey.isAcceptable -> {
                    val server = selectionKey.channel() as ServerSocketChannel
                    val clientChannel = server.accept()
                    clientChannel.configureBlocking(false)
                    clientChannel.register(selector, SelectionKey.OP_READ)
                    //记录下客户端信息
                    val key = clientChannel.socket().remoteSocketAddress.toString()
                    clientMap[key] = clientChannel
                    println("新客户端上线，当前所有在线的客户端为：$clientMap")
                    iterator.remove()
                }
                selectionKey.isReadable -> {
                    val clientChannel = selectionKey.channel() as SocketChannel
                    val byteBuffer = ByteBuffer.allocate(1024)
                    val count = clientChannel.read(byteBuffer)
                    if (count == -1) {
                        //先获取再关闭，不然会报错
                        val key = clientChannel.socket().remoteSocketAddress.toString()
                        clientChannel.close()
                        clientMap.remove(key)
                        println("客户端下线，当前所有在线的客户端为：$clientMap")
                    }
                    if (count > 0) {
                        byteBuffer.flip()
                        val recvMsg = String(Charsets.UTF_8.decode(byteBuffer).array())
                        println("client:$clientChannel --> $recvMsg")

                        val senderKey = clientChannel.socket().remoteSocketAddress.toString()
                        clientMap.forEach {
                            val writeBuffer = ByteBuffer.allocate(1024)

                            writeBuffer.put("来自${it.key}的消息：$recvMsg".toByteArray())
                            if (it.key!=senderKey) {
                                writeBuffer.rewind()
                                it.value.write(writeBuffer)
                            }
                        }
                    }
                    iterator.remove()
                }
            }

        }
    }
}