package org.adolf.zerocopy

import java.io.FileInputStream
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

fun main() {
    val socketChannel = SocketChannel.open()
    socketChannel.connect(InetSocketAddress("localhost", 8899))
    val fileChannel = FileInputStream("/home/adolf/Download/temp").channel
    val start = System.currentTimeMillis()
    var totalCount = 0L;
    fileChannel.use {
        //zero copy
        //零拷贝减少了用户态和内核态的切换，有两种方式：
        //1. 从硬盘拷贝到内核空间，然后从内核空间拷贝到socket buffer，再发送
        //2. 从硬盘拷贝到内核空间，然后修改socket buffer的文件描述符，添加数据内容所在的地址和长度，
        // 然后支持gather操作的网卡就会去对应的地址读取数据，比第一种省了一次数据拷贝。
        totalCount += fileChannel.transferTo(0, fileChannel.size(), socketChannel)
    }
    //totalCount:1073741824,cost:1140
    println("totalCount:$totalCount,cost:${System.currentTimeMillis() - start}")
    socketChannel.close()
}