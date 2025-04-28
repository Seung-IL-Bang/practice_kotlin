package io.demo.domain

import org.jetbrains.exposed.dao.id.LongIdTable

object UserEntity : LongIdTable() {
    val name = varchar("name", length = 50)
    val age = integer("age")
}