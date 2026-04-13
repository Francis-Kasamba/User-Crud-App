package com.example.usercrudapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import io.swagger.v3.oas.annotations.media.Schema

@Entity
@Table(name = "users")
@Schema(description = "User entity used for CRUD operations")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(description = "Unique user identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long = 0,
    
    @Column(nullable = false)
    @field:Schema(description = "Full name of the user", example = "Aarav Sharma")
    var name: String = "",
    
    @Column(nullable = false, unique = true)
    @field:Schema(description = "Unique email address", example = "aarav@example.com")
    var email: String = "",
    
    @Column(nullable = false)
    @field:Schema(description = "Age in years", example = "24", minimum = "0")
    var age: Int = 0,

    @OneToMany(mappedBy = "owner", cascade = [CascadeType.ALL], orphanRemoval = false)
    @JsonIgnore
    val ownedBooks: MutableList<Book> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = false)
    @JsonIgnore
    val borrows: MutableList<Borrow> = mutableListOf()
)
