package org.adolf.nio

import java.io.RandomAccessFile
import java.nio.channels.FileChannel

/**
 * MappedByteBuffer,对应操作系统中的内存映射。
 */
fun main() {
    val randomAccessFile = RandomAccessFile("NioTest9.txt", "rw")
    val channel:FileChannel = randomAccessFile.channel
    val mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5)
    mappedByteBuffer.put(0, 'a'.toByte())
    mappedByteBuffer.put(3, 'b'.toByte())
    randomAccessFile.close()
}