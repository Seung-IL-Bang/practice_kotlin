package io.demo.hellokotlin

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("MESSAGES")
data class Message(val text: String, @Id val id: String? = null)

/**
 * Message class will be used for data transfer: a list of serialized Message objects will make up the JSON document that the controller is going to respond to the browser request.
 *
 * [Data classes]
 * The main purpose of data classes in Kotlin is to hold data. Such classes are marked with the data keyword, and some standard functionality and some utility functions are often mechanically derivable from the class structure.
 *
 * [val and var properties]
 * Properties in Kotlin classes can be declared either as:
 *  1. mutable, using the var keyword
 *  2. read-only, using the val keyword
 * The Message class declares two properties using val keyword, the id and text. The compiler will automatically generate the getters for both of these properties. It will not be possible to reassign the values of these properties after an instance of the Message class is created.
 *
 * [Nullable types – String?]
 * Kotlin provides built-in support for nullable types. In Kotlin, the type system distinguishes between references that can hold null (nullable references) and those that cannot (non-nullable references).
 * For example, a regular variable of type String cannot hold null. To allow nulls, you can declare a variable as a nullable string by writing String?.
 * The id property of the Message class is declared as a nullable type this time. Hence, it is possible to create an instance of Message class by passing null as a value for id:
 */