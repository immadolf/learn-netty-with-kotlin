package org.adolf.nio

import java.io.RandomAccessFile

/**
 * 文件锁
 */
fun main() {
    val randomAccessFile = RandomAccessFile("NioTest9.txt", "rw")
    val fileLock = randomAccessFile.channel.lock(0, 3, true)
    println("valid: ${fileLock.isValid}")
    println("is shared lock : ${fileLock.isShared}")

    fileLock.release()
    randomAccessFile.close()
}