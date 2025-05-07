package io.hnr.restp.lessons

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val age: Int, val club: String)

val people = listOf<Person>(
    Person("Tiago", 18, "Porto"),
    Person("Hernâni", 42, "Porto"),
    Person("FIlipe", 41, "Benfica")
)

@Serializable
data class Pet(val id: Int, val name: String)

val pets = listOf<Pet>(
    Pet(1, "Phineas"),
    Pet(2, "Candace"),
    Pet(3, "Preto"),
    Pet(4, "Branquinho"),
    Pet(5, "Robin"),
    Pet(6, "Wally"),
    Pet(7, "Miguel"),
    Pet(8, "Lizzy"),
    Pet(9, "Eleven"),
    Pet(10, "Kitana"),
)

fun Route.lesson2() {

    get("/person/{name}") {
        val name = call.parameters["name"]!!
        val person = people.find { it.name == name } ?: return@get call.respondText(status = HttpStatusCode.NotFound) { "Person not found" }

        call.respond(person)
    }

    get("/pet/{id}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Invalid id" }
        val pet = pets.find { it.id == id } ?: return@get call.respondText(status = HttpStatusCode.NotFound) { "Pet not found" }
        call.respond(pet)
    }

    get("/pet") {
        val search = call.queryParameters["search"]

        val petList = if (search != null) {
            pets.filter { it.name.contains(search, ignoreCase = true) }
        } else {
            pets
        }
        call.respond(petList)
    }
}



