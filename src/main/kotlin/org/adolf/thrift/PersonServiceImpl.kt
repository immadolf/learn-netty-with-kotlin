package org.adolf.thrift

/**
 *
 *
 * @author adolf
 * @date 2020/6/24
 * @since
 */
class PersonServiceImpl : PersonService.Iface {
    override fun savePerson(person: Person) {

        println("Got Client Param: ")
        println(person.username)
        println(person.age)
        println(person.isMarried)
    }

    override fun getPersonByUsername(username: String): Person {
        println("Got Client Param: $username")

        val person = Person()
        person.setUsername(username)
        person.setAge(20)
        person.setMarried(false)

        return person
    }
}