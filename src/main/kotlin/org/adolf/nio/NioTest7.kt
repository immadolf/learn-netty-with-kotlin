package org.adolf.nio

import java.nio.ByteBuffer

/**
 * 只读Buffer.
 */
fun main() {

    val buffer = ByteBuffer.allocate(10)

    println(buffer.javaClass) //class java.nio.HeapByteBuffer

    for (i in 0 until buffer.capacity()) {
        buffer.put(i.toByte())
    }

    //readOnlyBuffer共享原Buffer的底层数据结构，但是position,limit,capacity是独立的
    val readOnlyBuffer = buffer.asReadOnlyBuffer()
    //readOnlyBuffer.put(1) 抛出ReadOnlyBufferException
    println(readOnlyBuffer.javaClass)//class java.nio.HeapByteBufferR
}