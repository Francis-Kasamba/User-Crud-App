package com.example.usercrudapp.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateBookRequest(
    @field:NotBlank(message = "title is required")
    val title: String,

    @field:NotNull(message = "ownerId is required")
    val ownerId: Long
)

data class UpdateBookRequest(
    @field:NotBlank(message = "title is required")
    val title: String,

    @field:NotNull(message = "ownerId is required")
    val ownerId: Long,

    @field:NotNull(message = "isAvailable is required")
    val isAvailable: Boolean
)

data class BookResponse(
    val id: Long,
    val title: String,
    val isAvailable: Boolean,
    val ownerId: Long,
    val ownerName: String
)