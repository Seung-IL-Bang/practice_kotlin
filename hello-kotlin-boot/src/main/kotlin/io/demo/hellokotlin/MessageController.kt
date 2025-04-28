package io.demo.hellokotlin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/")
class MessageController(private val service: MessageService) {

    @GetMapping
    fun getMessages() = service.findMessages()

    @PostMapping
    fun postMessage(@RequestBody message: Message): ResponseEntity<Message> {
        val savedMessage = service.save(message)
        return ResponseEntity.created(URI("/${savedMessage.id}")).body(savedMessage)
    }

    @GetMapping("/message")
    fun oneMessage(@RequestParam("name") name: String): String = "Hello, $name!"

    @GetMapping("/message/list")
    fun listMessages() = listOf(
        Message("1", "Hello!"),
        Message("2", "Welcome to Kotlin!"),
        Message("3", "Spring Boot is great!"), // A trailing comma is a comma symbol afte This is a convenient feature of Kotlin syntax and is entirely optional – your code will still work without them.r the last item of a series of elements:
    )
}

// $name expression is called a String template in Kotlin. String templates are String literals that contain embedded expressions.

/**
 * The Kotlin Standard Library provides implementations for basic collection types: sets, lists, and maps.
 * A pair of interfaces represents each collection type:
 *
 * A read-only interface that provides operations for accessing collection elements.
 *
 * A mutable interface that extends the corresponding read-only interface with write operations: adding, removing, and updating its elements.
 *
 * The corresponding factory functions are also provided by the Kotlin Standard Library to create instances of such collections.
 *
 * In this tutorial, you use the listOf() function to create a list of Message objects. This is the factory function to create a read-only list of objects: you can't add or remove elements from the list.
 * If it is required to perform write operations on the list, call the mutableListOf() function to create a mutable list instance.
 */