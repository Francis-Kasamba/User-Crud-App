package com.example.usercrudapp.service

import com.example.usercrudapp.dto.BorrowResponse

interface BorrowService {
    fun borrowBook(userId: Long, bookId: Long): BorrowResponse

    fun returnBook(bookId: Long): BorrowResponse

    fun getActiveBorrows(): List<BorrowResponse>
}