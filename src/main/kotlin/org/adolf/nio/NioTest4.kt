package org.adolf.nio

import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer

/**
 * 通过NIO读取文件的3个步骤
 * 1.从FileInputStream获取到FileChannel对象
 * 2.创建Buffer
 * 3.将数据从Channel读取到Buffer中
 *
 * Buffer中绝对方法与相对方法的含义：
 * 1.相对方法：limit值与position值会在操作时被考虑到。相对方法从当前position位置开始读/写一个或更多的元素，同时增加相应元素个数的position的值。如果读/写的过程中触及了limit，那么会抛出异常，此时不会发生数据传输。
 *
 * 2.绝对方法：完全忽略掉limit值与position值。绝对方法采用显式元素索引，并且不影响position的值。当索引的位置超过了limit的值时，将抛出IndexOutOfBoundsException
 *
 *
 */
fun main() {
    val inputStream = FileInputStream("input.txt")
    val outputStream = FileOutputStream("output.txt")

    val inputChannel = inputStream.channel
    val outputChannel = outputStream.channel

    val buffer = ByteBuffer.allocate(1024)
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