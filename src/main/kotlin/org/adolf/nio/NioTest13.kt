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

    //当解码和编码使用同一种字符集时，即使和源文件的字符集不同，也不会造成字节的丢失，
    //因此只要最终选择一个合适的字符集来显示，就不会乱码
    //例如NioTest13_in.txt的字符编码是UTF8，即使这里代码中使用ISO-8859-1，只要上下编解码的字符集一样，
    //最终生成的NioTest13_out.txt还是可以在UTF8字符集下正确的显示内容
    val decoder = Charsets.UTF_8.newDecoder()
    val encoder = Charsets.UTF_8.newEncoder()
    val charBuffer = decoder.decode(mappedByteBuffer)
    val outputData = encoder.encode(charBuffer)
    outputChannel.write(outputData)
    inputRandomAccessFile.close()
    outputRandomAccessFile.close()
}