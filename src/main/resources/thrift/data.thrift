namespace java org.adolf.thrift

//thrift.exe -out src/main/java/  --gen java src/main/resources/thrift/data.thrift
typedef i16 Short
typedef i32 Int
typedef i64 Long
typedef bool Boolean
typedef string String

struct Person {
    1:optional String username,
    2:optional Int age,
    3:optional Boolean married
}

exception DataException {
    1: optional String message,
    2: optional String callStack,
    3: optional String date
}

service PersonService {
    Person getPersonByUsername(1: required String username) throws (1: DataException dataException),

    void savePerson(1: required Person person) throws (1: DataException dataException)
}