package org.adolf.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

/**
 * selector.
 *
 * 自己的理解：
 * selector是多路复用选择器，在这里多路指的就是多个channel(准确的说是SelectableChannel类的实例),
 * 这里的复用指的是复用一个线程，这一个线程可以同时处理多个channel的状态，
 * 而选择器的作用就是选出这些channel中处于就绪状态的channel，
 * 既然选择器是选择channel的，那么选择器就必然和channel有联系，这个联系就是SelectionKey，
 * SelectionKey这个名字太抽象了，我的理解是如果选择器和channel是两个实体，那SelectionKey就是连着这两个实体的线条，
 * 可以从SelectionKey中得到对应的channel对象，这个也好理解，就像每条线条知道自己的端点在哪一样，
 * 还有一点是当通道注册到选择器时（也就是把通道和选择器连接起来的时候），需要声明自己感兴趣的事件是什么，这就像是订阅一样，首先要
 * 去订阅一些东西，比如公众号，服务端才会在后续发送更新给你，特别需要注意的是，每一类的channel都有自己的兴趣范围，不能随意声明事件，比如ServerSocketChannel，
 * 它能声明的事件只有OP_ACCEPT一种，这算是一种设计上的限制，毕竟一只猫的兴趣和一只狗的兴趣不会完全相同。
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
                loop@ while (true) {
                    buffer.clear()
                    val read = socketChannel.read(buffer)

                    when (read) {
                        // 当读不到数据的时候 read = 0
                        0 -> break@loop
                        // 当客户端断开连接时，服务端还是可以获取到可读事件，因为服务端无法感知连接是否断开，此时读出的数据长度为-1
                        // 当读到-1时，服务端需要主动关闭连接
                        -1 -> {
                            socketChannel.close()
                            break@loop
                        }
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