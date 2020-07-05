package org.adolf.zerocopy

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.ServerSocketChannel

fun main() {
    val serverSocketChannel = ServerSocketChannel.open()
    serverSocketChannel.bind(InetSocketAddress(8899))

    val serverSocket = serverSocketChannel.socket()

    while (true) {
        val clientSocket = serverSocket.accept()
        val buffer = ByteBuffer.allocate( 4 * 1024)
        while (true) {
            val readCount = clientSocket.channel.read(buffer)
            if (readCount == -1) {
                clientSocket.channel.close()
                println("读取完毕")
                break
            }
            //这里要对buffer进行清空
            buffer.rewind()
        }

    }
}