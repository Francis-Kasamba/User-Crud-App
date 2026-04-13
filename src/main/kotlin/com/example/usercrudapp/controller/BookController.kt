package com.example.usercrudapp.controller

import com.example.usercrudapp.dto.BookResponse
import com.example.usercrudapp.dto.CreateBookRequest
import com.example.usercrudapp.dto.UpdateBookRequest
import com.example.usercrudapp.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Book management APIs")
class BookController(private val bookService: BookService) {
    @GetMapping
    @Operation(summary = "Get all books")
    @ApiResponse(responseCode = "200", description = "Books fetched successfully")
    fun getAllBooks(): ResponseEntity<List<BookResponse>> = ResponseEntity.ok(bookService.getAllBooks())

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book found"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    fun getBookById(@Parameter(description = "Book ID", example = "1") @PathVariable id: Long): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.getBookById(id))
    }

    @PostMapping
    @Operation(summary = "Create a book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Book created"),
            ApiResponse(responseCode = "400", description = "Invalid request")
        ]
    )
    fun createBook(@Valid @RequestBody request: CreateBookRequest): ResponseEntity<BookResponse> {
        val created = bookService.createBook(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book updated"),
            ApiResponse(responseCode = "400", description = "Invalid request"),
            ApiResponse(responseCode = "404", description = "Book or owner not found")
        ]
    )
    fun updateBook(
        @Parameter(description = "Book ID", example = "1") @PathVariable id: Long,
        @Valid @RequestBody request: UpdateBookRequest
    ): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.updateBook(id, request))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Book deleted"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    fun deleteBook(@Parameter(description = "Book ID", example = "1") @PathVariable id: Long): ResponseEntity<Void> {
        bookService.deleteBook(id)
        return ResponseEntity.noContent().build()
    }
}