package com.example.usercrudapp.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import io.swagger.v3.oas.annotations.media.Schema

@Entity
@Table(name = "books")
@Schema(description = "Book owned by a user and available for borrowing")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(description = "Unique book identifier", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long = 0,

    @Column(nullable = false)
    @field:Schema(description = "Book title", example = "Kotlin in Action")
    val title: String,

    @Column(nullable = false)
    @field:Schema(description = "Whether this book can be borrowed right now", example = "true")
    var isAvailable: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User

    ,

    @OneToMany(mappedBy = "book")
    val borrows: MutableList<Borrow> = mutableListOf()
)