package io.demo.hellokotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloKotlinApplication

fun main(args: Array<String>) {
    runApplication<HelloKotlinApplication>(*args) // Since there is an array of strings, and you want to pass its content to the function, use the spread operator (prefix the array with a star sign *).
}

/**\
 * In Kotlin, if a class doesn't include any members (properties or functions), you can omit the class body ({}) for good.
 */