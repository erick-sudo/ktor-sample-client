package org.example

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {
    val client = HttpClient {
        install(WebSockets)
    }
    runBlocking {
        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/ktor-ws") {
            val messageOutputRoutine = launch {
                outputMessages()
            }

            val userInputRoutine = launch {
                inputMessages()
            }

            userInputRoutine.join() // Wait for completion or until the user exits
            messageOutputRoutine.cancelAndJoin()
        }
    }

    client.close()
    println("Connection closed. Goodbye!")
}

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for(message in incoming) {
            val text = message as? Frame.Text ?: continue
            println(text.readText())
        }
    } catch (e: Exception) {
        println("Error while receiving: " + e.localizedMessage)
    }
}

suspend fun DefaultClientWebSocketSession.inputMessages() {
    while (true) {
        val message = readlnOrNull() ?: ""

        if(message.equals("exit", true)) return
        try {
            send(message)
        } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }
}