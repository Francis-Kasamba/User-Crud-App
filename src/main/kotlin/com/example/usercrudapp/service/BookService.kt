package com.example.usercrudapp.service

import com.example.usercrudapp.dto.BookResponse
import com.example.usercrudapp.dto.CreateBookRequest
import com.example.usercrudapp.dto.UpdateBookRequest

interface BookService {
    fun getAllBooks(): List<BookResponse>

    fun getBookById(id: Long): BookResponse

    fun createBook(request: CreateBookRequest): BookResponse

    fun updateBook(id: Long, request: UpdateBookRequest): BookResponse

    fun deleteBook(id: Long)
}