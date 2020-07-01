package org.adolf.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

/**
 * selector ---- 不太能理解到底是啥模型
 */
fun main() {

    val ports = arrayOf(5000, 5001, 5002, 5003, 5004)

    val selector = Selector.open()

    for (port in ports) {
        val serverSocketChannel = ServerSocketChannel.open()
        serverSocketChannel.configureBlocking(false)
        val serverSocket = serverSocketChannel.socket()
        val address = InetSocketAddress(port)
        serverSocket.bind(address)

        //这里一定是OP_ACCEPT事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)
        println("监听端口： $port")
    }

    while (true) {
        //这个程序还有bug，当客户端使用ctrl+c断开之后，服务端会一直获取到可读事件
        val keyNumbers = selector.select()
        println("keyNumbers: $keyNumbers")

        val selectedKeys = selector.selectedKeys()
        println("selectedKeys: $selectedKeys")

        val iterator = selectedKeys.iterator()

        while (iterator.hasNext()) {
            val selectionKey = iterator.next()
            if (selectionKey.isAcceptable) {
                val serverSocketChannel = selectionKey.channel() as ServerSocketChannel
                val socketChannel = serverSocketChannel.accept()
                socketChannel.configureBlocking(false)

                socketChannel.register(selector, SelectionKey.OP_READ)
                iterator.remove()
                println("获得客户端连接: $socketChannel")
            } else if (selectionKey.isReadable) {
                val socketChannel = selectionKey.channel() as SocketChannel
                var bytesRead = 0
                val buffer = ByteBuffer.allocate(1024)
                while (true) {
                    buffer.clear()
                    val read = socketChannel.read(buffer)
                    // 当读不到数据的时候 read = 0
                    if (read <= 0) {
                        break
                    }
                    bytesRead += read
                    buffer.flip()
                    socketChannel.write(buffer)
                }
                println("读取：$bytesRead,来自于：$socketChannel")
                iterator.remove()
            }
        }
    }
}