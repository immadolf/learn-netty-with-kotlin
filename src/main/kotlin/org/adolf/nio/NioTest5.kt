package org.adolf.nio

import java.nio.ByteBuffer

/**
 * ByteBuffer类型化的put与get方法，放入和取出的顺序需要一致。
 */
fun main() {
    val buffer = ByteBuffer.allocate(64)

    buffer.putInt(15)
    buffer.putLong(5000000000L)
    buffer.putDouble(3.1415)
    buffer.putChar('你')
    buffer.putShort(2)

    buffer.flip()

    println(buffer.getInt())
    println(buffer.getLong())
    println(buffer.getDouble())
    println(buffer.getChar())
    println(buffer.getShort())
}