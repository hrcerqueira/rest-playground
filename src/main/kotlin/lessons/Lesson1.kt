package io.hnr.restp.lessons

import io.github.serpro69.kfaker.commerce.CommerceFaker
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val age: Int, val club: String)

fun Route.lesson1() {

    val food = CommerceFaker().food

    get("/param/name/{name}") {
        val name = call.parameters["name"]!!
        call.respondText("Hello $name, do you like ${food.dish()}?")
    }

    val goodClubs = listOf("Porto", "FCP")
    val badClubs = listOf("Benfica", "SLB", "Sporting", "SCP")

    fun List<String>.containsIgnoreCase(other: String) = this.any { it.equals(other, ignoreCase = true) }

    fun StringValues.readPerson(): Person {
        val name = this["name"] ?: throw BadRequestException("Missing name")
        val age = (this["age"] ?: throw BadRequestException("Missing age"))
            .toIntOrNull() ?: throw BadRequestException("Invalid age")

        val club = this["club"] ?: throw BadRequestException("Missing club")
        return Person(name, age, club)
    }

    suspend fun RoutingContext.responseWithGreeting(person: Person) = with(person) {
        val response  = when {
            goodClubs.containsIgnoreCase(club)
                -> "Hey Hey ${name}, you have impeccable taste in football clubs!"
            badClubs.containsIgnoreCase(club) && age < 12 -> "Hi ${name}, I think you will grow out of liking ${club} very soon."
            badClubs.containsIgnoreCase(club) -> "Hey ${name}, I am not into ${club}, like, at all!!"
            else -> "Hey ${name}, I am not sure I like ${club}... Not even sure I know it!"
        }
        call.respondText(response)
    }

    get("/params/name/{name}/age/{age}/club/{club}") {
       responseWithGreeting(call.parameters.readPerson())
    }

    get("/query") {
        responseWithGreeting(call.request.queryParameters.readPerson())
    }

    get("/headers") {
        responseWithGreeting(call.request.headers.readPerson())
    }


    get("/structured/name/{name}/age/{age}/club/{club}") {
        call.respond(call.parameters.readPerson())
    }


}



