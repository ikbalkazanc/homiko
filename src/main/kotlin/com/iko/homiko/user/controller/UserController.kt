package com.iko.homiko.user.controller

import com.iko.homiko.user.dto.CreateUserRequest
import com.iko.homiko.user.dto.UserResponse
import com.iko.homiko.user.entity.User
import com.iko.homiko.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userRepository: UserRepository,
) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        log.info("GET /api/users - Fetching all users")
        val users = userRepository.findAll()
        return ResponseEntity.ok(users.map { UserResponse.from(it) })
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        log.info("GET /api/users/{} - Fetching user by id", id)
        val user = userRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(UserResponse.from(user))
    }

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<Any> {
        log.info("POST /api/users - Creating user with email: {}", request.email)

        if (userRepository.existsByEmail(request.email)) {
            log.warn("POST /api/users - Email already exists: {}", request.email)
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(mapOf("error" to "Email already exists"))
        }

        val user = User(
            name = request.name,
            email = request.email,
        )

        val savedUser = userRepository.save(user)
        log.info("POST /api/users - User created with id: {}", savedUser.id)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UserResponse.from(savedUser))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        log.info("DELETE /api/users/{} - Deleting user", id)

        if (!userRepository.existsById(id)) {
            log.warn("DELETE /api/users/{} - User not found", id)
            return ResponseEntity.notFound().build()
        }

        userRepository.deleteById(id)
        log.info("DELETE /api/users/{} - User deleted successfully", id)
        return ResponseEntity.noContent().build()
    }
}
