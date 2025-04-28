package io.demo.api.v1.controller

data class UserUpdateRequestForm(
    val name: String? = null,
    val age: Int? = null,
)