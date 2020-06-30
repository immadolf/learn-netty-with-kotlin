package org.adolf.nio

import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer

fun main() {
    val inputStream = FileInputStream("input.txt")
    val outputStream = FileOutputStream("output.txt")

    val inputChannel = inputStream.channel
    val outputChannel = outputStream.channel

    //堆外内存
    val buffer = ByteBuffer.allocateDirect(1024)
    //val buffer = ByteBuffer.wrap(byteArrayOf(1, 2, 3, 4, 5)) 可以用wrap方法指定buffer的底层数组
    while (true) {
        buffer.clear()

        val readBytes = inputChannel.read(buffer)
        println("readBytes:$readBytes")
        if (-1 == readBytes) {
            break
        }

        buffer.flip()

        outputChannel.write(buffer)
    }

    inputStream.close()
    outputStream.close()
}