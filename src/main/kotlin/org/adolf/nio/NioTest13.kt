package org.adolf.nio

import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel

/**
 * 字符集.
 */
fun main() {
    val inputFile = "NioTest13_in.txt"
    val outputFile = "NioTest13_out.txt"

    val inputRandomAccessFile = RandomAccessFile(inputFile, "r")
    val outputRandomAccessFile = RandomAccessFile(outputFile, "rw")

    val inputLength = File(inputFile).length()
    val inputChannel = inputRandomAccessFile.channel
    val outputChannel = outputRandomAccessFile.channel
    val mappedByteBuffer = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength)

    val decoder = Charsets.UTF_8.newDecoder()
    val encoder = Charsets.UTF_8.newEncoder()
    val charBuffer = decoder.decode(mappedByteBuffer)
    val outputData = encoder.encode(charBuffer)
    outputChannel.write(outputData)
    inputRandomAccessFile.close()
    outputRandomAccessFile.close()
}