package io.demo.api.v1.controller

import io.demo.domain.UserId
import io.demo.service.UserCreateRequest
import io.demo.service.UserService
import io.demo.service.UserUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun findUserById(
        @PathVariable("id") id: Long
    ): ResponseEntity<UserResponse> {
        val user = userService.findUserById(UserId(id))

        return if (user != null) {
            ResponseEntity.ok(
                UserResponse(
                    id = user.id.value,
                    name = user.name,
                    age = user.age
                )
            )
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(
        @RequestBody form: UserCreateRequestForm
    ): ResponseEntity<UserCreateResponse> {
        val userId = userService.create(
            UserCreateRequest(
                name = form.name,
                age = form.age
            )
        )
        return ResponseEntity.ok(
            UserCreateResponse(
                id = userId.value
            )
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody form: UserUpdateRequestForm
    ): ResponseEntity<Unit> {
        userService.update(
            id = id,
            request = UserUpdateRequest(
                name = form.name,
                age = form.age
            )
        )

        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Long
    ): ResponseEntity<Unit> {
        userService.delete(UserId(id))
        return ResponseEntity.noContent().build()
    }
}