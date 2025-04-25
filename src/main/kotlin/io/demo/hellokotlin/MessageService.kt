package io.demo.hellokotlin

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class MessageService(private val db: JdbcTemplate) {

    fun findMessages(): List<Message> = db.query("select * from messages") { rs, _ ->
        Message(rs.getString("id"), rs.getString("text"))
    }

    fun save(message: Message): Message {
        db.update("insert into messages values (?, ?)", message.id, message.text)
        return message;
    }
}