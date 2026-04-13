package com.example.usercrudapp.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "borrows")
@Schema(description = "Borrow transaction history for a book")
data class Borrow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(description = "Unique borrow transaction id", example = "55", accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    val book: Book,

    @Column(nullable = false)
    val borrowDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var returnDate: LocalDateTime? = null,

    @Column(nullable = false)
    var isActive: Boolean = true
)