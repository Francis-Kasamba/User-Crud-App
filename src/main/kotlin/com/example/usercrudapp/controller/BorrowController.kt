package com.example.usercrudapp.controller

import com.example.usercrudapp.dto.BorrowRequest
import com.example.usercrudapp.dto.BorrowResponse
import com.example.usercrudapp.dto.ReturnBookRequest
import com.example.usercrudapp.service.BorrowService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/borrows")
@Tag(name = "Borrowing", description = "Borrow/return APIs")
class BorrowController(private val borrowService: BorrowService) {
    @GetMapping("/active")
    @Operation(summary = "Check all currently borrowed books")
    @ApiResponse(responseCode = "200", description = "Active borrows fetched successfully")
    fun getActiveBorrows(): ResponseEntity<List<BorrowResponse>> {
        return ResponseEntity.ok(borrowService.getActiveBorrows())
    }

    @PostMapping("/borrow")
    @Operation(summary = "Borrow a book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book borrowed successfully"),
            ApiResponse(responseCode = "400", description = "Invalid borrow request"),
            ApiResponse(responseCode = "404", description = "User or book not found"),
            ApiResponse(responseCode = "409", description = "Book already borrowed")
        ]
    )
    fun borrowBook(@Valid @RequestBody request: BorrowRequest): ResponseEntity<BorrowResponse> {
        return ResponseEntity.ok(borrowService.borrowBook(request.userId, request.bookId))
    }

    @PostMapping("/return")
    @Operation(summary = "Return a borrowed book")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book returned successfully"),
            ApiResponse(responseCode = "400", description = "Book is not currently borrowed"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    fun returnBook(@Valid @RequestBody request: ReturnBookRequest): ResponseEntity<BorrowResponse> {
        return ResponseEntity.ok(borrowService.returnBook(request.bookId))
    }
}