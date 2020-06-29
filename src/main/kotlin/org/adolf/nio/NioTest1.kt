package org.adolf.nio

import java.nio.IntBuffer
import java.security.SecureRandom

/**
 * nio buffer的使用.
 * java.io中最核心的一个概念是stream（流），面向流编程，在java中，一个流要么是输入流，
 * 要么是输出流，不可能同时既是输入流又是输出流。
 *
 * java.nio中拥有3个核心概念：Selector，Channel与Buffer。在nio中，我们是面向块或者缓冲区编程的。
 * Buffer本身就是一块内存，底层实现上使用的是数组，数据的读和写都是通过Buffer来实现的。
 *
 * 除了数组之外，Buffer还提供了对于数据的结构化访问方式，并且可以追踪到系统的读写过程。
 *
 * Java中的8种原生类型都有相应的Buffer类型，如IntBuffer，LongBuffer。
 *
 * Channel指的是可以向其写入数据或是从中读取数据的对象，它有点类似于java.io中的stream。
 * 所有数据都是通过Buffer来进行的，永远不会出现直接向Channel写入数据的情况，或是直接从Channel读取数据的情况。
 * 与Stream不同的是，Channel是双向的，一个流只能是InputStream或者是OutputStream，但是Channel打开后可以进行读取、写入、读写。
 * 由于Channel是双向的，因此它能更好的反映底层操作系统的真实情况。
 */
fun main() {
    val intBuffer = IntBuffer.allocate(10)

    for (i in 0 until intBuffer.capacity()) {
        val number = SecureRandom().nextInt(10)
        intBuffer.put(number)
    }

    //进行buffer的读写切换
    intBuffer.flip()

    while (intBuffer.hasRemaining()) {
        println(intBuffer.get())
    }


}