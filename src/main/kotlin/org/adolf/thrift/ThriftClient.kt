package org.adolf.thrift

import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.transport.TFramedTransport
import org.apache.thrift.transport.TSocket

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */
fun main() {
    val transport = TFramedTransport(TSocket("localhost", 8899), 600)

    val protocol = TCompactProtocol(transport)
    val client = PersonService.Client(protocol)

    try {
        transport.open()
        val person = client.getPersonByUsername("张三")
        println(person.toString())

        println("---------------")

        val person2 = Person()
        person2.setUsername("李四")
        person2.setAge(22)//这里不能用 person2.age 这样的用法，因为在set方法中有额外的处理
        person2.setMarried(true)// 同上

        println(person2.toString())
        client.savePerson(person2)

    } catch (ex: Exception) {
        ex.printStackTrace()
    } finally {
        transport.close()
    }
}