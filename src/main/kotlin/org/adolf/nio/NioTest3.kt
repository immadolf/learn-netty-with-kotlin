package org.adolf.nio

import java.io.FileOutputStream
import java.nio.ByteBuffer

fun main() {
    val fos = FileOutputStream("NioTest3.txt")
    val channel = fos.channel
    val buffer = ByteBuffer.allocate(512)
    val string = "hello,world,welcome"
    buffer.put(string.toByteArray())
    buffer.flip()
    channel.write(buffer)
    fos.close()
}