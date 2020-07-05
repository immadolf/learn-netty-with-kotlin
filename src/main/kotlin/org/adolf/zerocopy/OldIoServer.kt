package org.adolf.zerocopy

import java.net.ServerSocket

fun main() {
    val serverSocket = ServerSocket(8899)
    serverSocket.reuseAddress = true

    while (true) {
        val clientSocket = serverSocket.accept()
        val inputStream = clientSocket.getInputStream()

        val buffer = ByteArray(4 * 1024)

        while (true) {
            if (inputStream.read(buffer) == -1) {
                println("读取完毕")
                break
            }
        }
    }
}