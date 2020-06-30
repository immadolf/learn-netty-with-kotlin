package org.adolf.nio

import java.nio.ByteBuffer

/**
 * Buffer的切片（底层共享同一块数据）
 */
fun main() {
    val buffer = ByteBuffer.allocate(10)

    for (i in 0 until buffer.capacity()) {
        buffer.put(i.toByte())
    }

    //指定切片的起点和终点，左闭右开
    buffer.position(2)
    buffer.limit(6)
    val slice = buffer.slice()

    for (i in 0 until slice.capacity()) {
        slice.put(i, (slice.get(i) * 2).toByte())
    }

    //重置position和limit，不会清除数据
    buffer.clear()
    while (buffer.hasRemaining()) {
        println(buffer.get())
    }

}