package io.hnr.restp.lessons

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.lesson1() {

    get("/param/name/{name}") {
        val name = call.parameters["name"]!!
        call.respondText("Hello $name")
    }

    get("/params/name/{name}/age/{age}/club/{club}") {

        val name = call.parameters["name"]!!
        val age = call.parameters["age"]!!

        age.toIntOrNull() ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Invalid age" }


        val club = call.parameters["club"]!!
        call.respondText("Your name is $name, your age is $age and your club is $club")
    }

    get("/query") {

        val name = call.queryParameters["name"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Missing name" }
        val age = call.queryParameters["age"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Missing name" }

        age.toIntOrNull() ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Invalid age" }


        val club = call.queryParameters["club"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Missing name" }

        call.respondText("Your name is $name, your age is $age and your club is $club")
    }

    get("/headers") {

        val name = call.request.headers["name"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Missing name" }
        val age = call.request.headers["age"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Missing name" }

        age.toIntOrNull() ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Invalid age" }


        val club = call.request.headers["club"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Missing name" }

        call.respondText("Your name is $name, your age is $age and your club is $club")
    }


    get("/structured/name/{name}/age/{age}/club/{club}") {

        val name = call.parameters["name"]!!
        val age = call.parameters["age"]!!.toIntOrNull() ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "Invalid age" }


        val club = call.parameters["club"]!!
        val person = Person(name, age, club)

        call.respond(person)
    }


}



