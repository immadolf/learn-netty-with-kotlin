package org.adolf.protobuf

fun main() {
    val student = StudentProtos.Student.newBuilder().setAge(20).setName("张三").setAddress("北京").build()

    val studentByteArray = student.toByteArray()

    val student2 = StudentProtos.Student.parseFrom(studentByteArray)

    println(student2.name)
    println(student2.age)
    println(student2.address)

}