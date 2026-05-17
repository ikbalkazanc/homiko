package com.iko.homiko.user.dto

import com.iko.homiko.user.entity.User
import java.time.Instant

data class CreateUserRequest(
    val name: String,
    val email: String,
)

data class UpdateUserRequest(
    val name: String?,
    val email: String?,
)

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    companion object {
        fun from(user: User): UserResponse = UserResponse(
            id = user.id!!,
            name = user.name,
            email = user.email,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
        )
    }
}
