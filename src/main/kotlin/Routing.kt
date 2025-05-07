package io.hnr.restp

import io.hnr.restp.lessons.lesson1
import io.hnr.restp.lessons.lesson2
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(DoubleReceive)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/double-receive") {
            val first = call.receiveText()
            val theSame = call.receiveText()
            call.respondText(first + " " + theSame)
        }

        route("/api/l1") {
            lesson1()
        }

        route("/api/l2") {
            lesson2()
        }

    }
}
