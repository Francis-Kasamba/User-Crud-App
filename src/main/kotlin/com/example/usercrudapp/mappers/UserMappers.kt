package com.example.usercrudapp.mappers

import com.example.usercrudapp.dto.UserResponse
import com.example.usercrudapp.model.User
import java.time.Instant
import java.util.Date

// review extension functions
fun User.toResponse(): UserResponse {
    // do age calculation here and convert to Int
    // Review Instant, Date and DateTime
    // review inversion of control (IoC) principle
    val age = Instant.now()
    return UserResponse(id.toInt(), name = this.name, age = age)
}


fun String.toHelloWorld() = "Hello $this"


fun greetFrancis() {
    println("Francis".toHelloWorld())
}

fun main() {
    greetFrancis()
}







var circumfrenceOfTheCircle: Int = 20;


