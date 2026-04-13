package com.example.usercrudapp.repository

import com.example.usercrudapp.model.Borrow
import org.springframework.data.jpa.repository.JpaRepository

interface BorrowRepository : JpaRepository<Borrow, Long> {
    fun findByBookIdAndIsActiveTrue(bookId: Long): Borrow?

    fun existsByBookIdAndIsActiveTrue(bookId: Long): Boolean

    fun findAllByIsActiveTrue(): List<Borrow>
}