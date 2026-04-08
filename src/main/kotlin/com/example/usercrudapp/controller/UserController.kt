package com.example.usercrudapp.controller

import com.example.usercrudapp.model.User
import com.example.usercrudapp.repository.UserRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management APIs")
class UserController(private val userRepository: UserRepository) {

    // GET all users
    @GetMapping
    @Operation(summary = "Get all users", description = "Returns the complete list of users")
    @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    fun getAllUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userRepository.findAll())
    }

    // GET a user by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns one user if the ID exists")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User found"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun getUserById(@Parameter(description = "User ID", example = "1") @PathVariable id: Long): ResponseEntity<User> {
        return userRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    // CREATE a new user
    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "User created"),
            ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content(schema = Schema())])
        ]
    )
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userRepository.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser)
    }

    // UPDATE an existing user
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "User updated"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun updateUser(
        @Parameter(description = "User ID", example = "1") @PathVariable id: Long,
        @RequestBody user: User
    ): ResponseEntity<User> {
        return if (userRepository.existsById(id)) {
            val updatedUser = user.copy(id = id)
            ResponseEntity.ok(userRepository.save(updatedUser))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // DELETE a user
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "User deleted"),
            ApiResponse(responseCode = "404", description = "User not found")
        ]
    )
    fun deleteUser(@Parameter(description = "User ID", example = "1") @PathVariable id: Long): ResponseEntity<Void> {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
