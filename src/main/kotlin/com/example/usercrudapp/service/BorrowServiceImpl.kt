package com.example.usercrudapp.service

import com.example.usercrudapp.dto.BorrowResponse
import com.example.usercrudapp.exception.BadRequestException
import com.example.usercrudapp.exception.ConflictException
import com.example.usercrudapp.exception.NotFoundException
import com.example.usercrudapp.model.Borrow
import com.example.usercrudapp.repository.BookRepository
import com.example.usercrudapp.repository.BorrowRepository
import com.example.usercrudapp.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BorrowServiceImpl(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val borrowRepository: BorrowRepository
) : BorrowService {
    @Transactional
    override fun borrowBook(userId: Long, bookId: Long): BorrowResponse {
        val user = userRepository.findById(userId).orElseThrow { NotFoundException("User with id $userId not found") }

        val book = bookRepository.findByIdForUpdate(bookId)
            ?: throw NotFoundException("Book with id $bookId not found")

        if (book.owner.id == userId) {
            throw BadRequestException("Owner cannot borrow their own book")
        }

        if (!book.isAvailable || borrowRepository.existsByBookIdAndIsActiveTrue(bookId)) {
            throw ConflictException("Book with id $bookId is already borrowed")
        }

        book.isAvailable = false
        bookRepository.save(book)

        val borrow = borrowRepository.save(
            Borrow(
                user = user,
                book = book,
                borrowDate = LocalDateTime.now(),
                isActive = true
            )
        )
        return borrow.toResponse()
    }

    @Transactional
    override fun returnBook(bookId: Long): BorrowResponse {
        val book = bookRepository.findByIdForUpdate(bookId)
            ?: throw NotFoundException("Book with id $bookId not found")

        val activeBorrow = borrowRepository.findByBookIdAndIsActiveTrue(bookId)
            ?: throw BadRequestException("Book with id $bookId is not currently borrowed")

        activeBorrow.isActive = false
        activeBorrow.returnDate = LocalDateTime.now()
        book.isAvailable = true

        bookRepository.save(book)
        val savedBorrow = borrowRepository.save(activeBorrow)
        return savedBorrow.toResponse()
    }

    @Transactional(readOnly = true)
    override fun getActiveBorrows(): List<BorrowResponse> {
        return borrowRepository.findAllByIsActiveTrue().map { it.toResponse() }
    }

    private fun Borrow.toResponse(): BorrowResponse = BorrowResponse(
        id = id,
        userId = user.id,
        bookId = book.id,
        borrowDate = borrowDate,
        returnDate = returnDate,
        isActive = isActive
    )
}