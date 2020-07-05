package org.adolf.zerocopy

import java.io.File
import java.io.FileInputStream
import java.net.Socket

fun main() {

    //createSendFile()
    val fis = FileInputStream("/home/adolf/Download/temp")
    val socket = Socket("localhost", 8899)
    val outputStream = socket.getOutputStream()

    val start = System.currentTimeMillis()
    fis.use {
        outputStream.use {
            val buffer = ByteArray(4 * 1024)

            var totalCount = 0
            while (true) {
                val readCount = fis.read(buffer)
                if (readCount == -1) {
                    break
                }
                totalCount += readCount
                outputStream.write(buffer)
            }

            //totalCount:1073741824,cost:1378
            println("totalCount:$totalCount,cost:${System.currentTimeMillis() - start}")
        }
    }
}

fun createSendFile(): File {

    //1G
    val fileSize = 1 shl 30
    println(fileSize)

    val sendFile = File("/home/adolf/Download/temp")

    val byteArray = ByteArray(1024) { 0 }

    sendFile.outputStream().use {
        for (i in 1..fileSize step 1024) {
            it.write(byteArray)
        }
    }

    return sendFile
}