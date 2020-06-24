package org.adolf.thrift

import org.apache.thrift.TProcessorFactory
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.server.THsHaServer
import org.apache.thrift.transport.TFramedTransport
import org.apache.thrift.transport.TNonblockingServerSocket

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */

fun main() {
    val socket = TNonblockingServerSocket(8899)
    val args = THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4)
    val processor = PersonService.Processor<PersonServiceImpl>(PersonServiceImpl())

    args.protocolFactory(TCompactProtocol.Factory())
    args.transportFactory(TFramedTransport.Factory())
    args.processorFactory(TProcessorFactory(processor))

    val server = THsHaServer(args)
    println("Thrift Server Started")
    server.serve()
}