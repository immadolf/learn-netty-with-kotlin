package org.adolf.nio

import java.io.FileInputStream
import java.nio.ByteBuffer

fun main() {
    val fis = FileInputStream("NioTest2.txt")
    val fileChannel = fis.channel

    val buffer = ByteBuffer.allocate(512)
    fileChannel.read(buffer)

    buffer.flip()

    while (buffer.hasRemaining()) {
        val byte = buffer.get()
        println("Character: ${byte.toChar()}")
    }

    fis.close()
}