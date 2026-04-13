package com.example.usercrudapp.service

import com.example.usercrudapp.dto.BookResponse
import com.example.usercrudapp.dto.CreateBookRequest
import com.example.usercrudapp.dto.UpdateBookRequest
import com.example.usercrudapp.exception.NotFoundException
import com.example.usercrudapp.model.Book
import com.example.usercrudapp.repository.BookRepository
import com.example.usercrudapp.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository
) : BookService {
    @Transactional(readOnly = true)
    override fun getAllBooks(): List<BookResponse> = bookRepository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    override fun getBookById(id: Long): BookResponse {
        val book = bookRepository.findById(id).orElseThrow { NotFoundException("Book with id $id not found") }
        return book.toResponse()
    }

    @Transactional
    override fun createBook(request: CreateBookRequest): BookResponse {
        val owner = userRepository.findById(request.ownerId)
            .orElseThrow { NotFoundException("Owner user with id ${request.ownerId} not found") }

        val saved = bookRepository.save(
            Book(
                title = request.title.trim(),
                isAvailable = true,
                owner = owner
            )
        )
        return saved.toResponse()
    }

    @Transactional
    override fun updateBook(id: Long, request: UpdateBookRequest): BookResponse {
        val existing = bookRepository.findById(id).orElseThrow { NotFoundException("Book with id $id not found") }
        val owner = userRepository.findById(request.ownerId)
            .orElseThrow { NotFoundException("Owner user with id ${request.ownerId} not found") }

        val updated = existing.copy(
            title = request.title.trim(),
            isAvailable = request.isAvailable,
            owner = owner
        )
        return bookRepository.save(updated).toResponse()
    }

    @Transactional
    override fun deleteBook(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw NotFoundException("Book with id $id not found")
        }
        bookRepository.deleteById(id)
    }

    private fun Book.toResponse(): BookResponse = BookResponse(
        id = id,
        title = title,
        isAvailable = isAvailable,
        ownerId = owner.id,
        ownerName = owner.name
    )
}