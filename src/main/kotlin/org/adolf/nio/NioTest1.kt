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
 * Java中的7种原生类型都有相应的Buffer类型，如IntBuffer，LongBuffer。没有BooleanBuffer
 *
 * Channel指的是可以向其写入数据或是从中读取数据的对象，它有点类似于java.io中的stream。
 * 所有数据都是通过Buffer来进行的，永远不会出现直接向Channel写入数据的情况，或是直接从Channel读取数据的情况。
 * 与Stream不同的是，Channel是双向的，一个流只能是InputStream或者是OutputStream，但是Channel打开后可以进行读取、写入、读写。
 * 由于Channel是双向的，因此它能更好的反映底层操作系统的真实情况。
 *
 * Buffer中三个核心概念的理解：
 * 1. capacity：buffer的最大容量，永远不会变且不会为负数
 * 2. limit: 指向第一个不能被读或写的元素，初始时等于capacity,且永远<=capacity并且不会为负数，执行flip方法时，limit会指向当前position的位置
 * 3. position： 指向下一个被读或写的元素，永远<=limit并且不会为负数，执行flip方法时，position会被赋值为0
 */
fun main() {
    val intBuffer = IntBuffer.allocate(10)

    println("capacity:${intBuffer.capacity()}")

    for (i in 0 until 5) {
        val number = SecureRandom().nextInt(10)
        intBuffer.put(number)
    }

    println("before flip, limit:${intBuffer.limit()}")

    //进行buffer的读写切换
    intBuffer.flip()

    println("after flip, limit:${intBuffer.limit()}")

    while (intBuffer.hasRemaining()) {
        println(intBuffer.get())
        println("position:${intBuffer.position()}")
        println("limit:${intBuffer.limit()}")
        println("capacity:${intBuffer.capacity()}")
    }


}