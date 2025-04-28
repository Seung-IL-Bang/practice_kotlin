package io.demo.service

import io.demo.domain.User
import io.demo.domain.UserEntity
import io.demo.domain.UserId
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService {

    // read user by user primary key
    fun findUserById(id: UserId): User? {
        return UserEntity
            .selectAll()
            .where {
                UserEntity.id eq id.value
            }
            .firstOrNull()?.let {
                User(
                    id = UserId(it[UserEntity.id].value),
                    name = it[UserEntity.name],
                    age = it[UserEntity.age]
                )
            }
    }

    // create user
    fun create(request: UserCreateRequest): UserId {
        val id = UserEntity.insertAndGetId {
            it[name] = request.name
            it[age] = request.age
        }

        return UserId(id.value)
    }

    // update user
    fun update(id: Long, request: UserUpdateRequest) {
        UserEntity.update({UserEntity.id eq id})  {
            request.name?.let { name -> it[UserEntity.name] = name }
            request.age?.let { age -> it[UserEntity.age] = age }
        }
    }

    // delete user
    fun delete(id: UserId) {
        UserEntity.deleteWhere { UserEntity.id eq id.value }
    }
}