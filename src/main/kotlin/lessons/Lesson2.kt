package io.hnr.restp.lessons

import io.github.serpro69.kfaker.humor.HumorFaker
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    val id: Int?,
    val who: Person,
    val sentence: String
)

val people = mapOf<String, Person>(
    "chuck" to Person("Chuck Norris", 85, "Expendables"),
    "mitch" to Person("Mitch Hedberg", 37, "Night Club"),
    "jack" to Person("Jack Handey", 76, "Chess Club"),
    "chiquito" to Person("Chiquito", 65, "Mexico?")
)

val humor = HumorFaker()

val sentences = listOf(
    1.until(6).map { Sentence(it, people["chuck"]!!, humor.chuckNorris.fact()) },
    1.until(6).map { Sentence(it + 5, people["mitch"]!!, humor.mitchHedberg.quote()) },
    1.until(6).map { Sentence(it + 10, people["jack"]!!, humor.jackHandey.quote()) },
    1.until(6).map { Sentence(it + 15, people["chiquito"]!!, humor.chiquito.sentences()) },
).flatten()

fun Route.lesson2() {

    get("/people") {
        call.respond(people)
    }

    get("/person/{name}") {
        val name = call.parameters["name"]!!
        val person = people[name] ?: return@get call.respondText(status = HttpStatusCode.NotFound) { "Person not found" }

        call.respond(person)
    }

    get("/sentence/{id}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Invalid id" }
        val sentence = sentences.find { it.id == id } ?: return@get call.respondText(status = HttpStatusCode.NotFound) { "Sentence not found" }
        call.respond(sentence)
    }

    get("/sentence") {
        val search = call.queryParameters["search"]

        val list = if (search != null) {
            sentences.filter { it.sentence.contains(search, ignoreCase = true) }
        } else {
            sentences
        }
        call.respond(list)
    }
}



