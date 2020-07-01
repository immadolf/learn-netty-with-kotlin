package org.adolf.nio

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.ServerSocketChannel

/**
 * 关于Buffer的Scattering与Gathering.
 * Scattering：依次将数据读入多个Buffer
 * Gathering：依次将数据写入多个Buffer
 *
 * 启动后可用nc命令进行连接：nc localhost 8899
 * 然后输入n个字符，当n>=9时，屏幕上才会打印
 */
fun main() {
    val serverSocketChannel = ServerSocketChannel.open()
    val address = InetSocketAddress(8899)
    serverSocketChannel.socket().bind(address)

    val messageLength = 2 + 3 + 4

    val buffers = arrayOfNulls<ByteBuffer>(3)
    buffers[0] = ByteBuffer.allocate(2)
    buffers[1] = ByteBuffer.allocate(3)
    buffers[2] = ByteBuffer.allocate(4)

    val socketChannel = serverSocketChannel.accept()

    while (true) {
        var bytesRead = 0L
        while (bytesRead < messageLength) {
            bytesRead += socketChannel.read(buffers)

            println("bytesRead: $bytesRead")
            buffers.map { "position:${it?.position()},limit:${it?.limit()}" }
                .forEach(System.out::println)
        }
        buffers.forEach { byteBuffer -> byteBuffer?.flip() }

        var bytesWritten = 0L
        while (bytesWritten < messageLength) {
            bytesWritten += socketChannel.write(buffers)
            println("bytesWritten: $bytesWritten")
        }
        buffers.forEach { byteBuffer -> byteBuffer?.clear() }
    }
}