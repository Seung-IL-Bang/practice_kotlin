package io.demo.hellokotlin

import org.springframework.data.repository.CrudRepository

interface MessageRepository: CrudRepository<Message, String> {
}