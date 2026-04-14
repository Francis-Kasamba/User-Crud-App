package com.example.usercrudapp.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class BorrowRequest(
    @field:NotNull(message = "userId is required")
    val userId: Long,

    @field:NotNull(message = "bookId is required")
    val bookId: Long
)

data class ReturnBookRequest(
    @field:NotNull(message = "bookId is required")
    val bookId: Long
)

data class BorrowResponse(
    val id: Long,
    val userId: Long,
    val bookId: Long,
    val borrowDate: LocalDateTime,
    val returnDate: LocalDateTime?,
    val isActive: Boolean
)