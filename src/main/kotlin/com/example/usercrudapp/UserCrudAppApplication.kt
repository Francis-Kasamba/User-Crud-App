package com.example.usercrudapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserCrudAppApplication

fun main(args: Array<String>) {
    runApplication<UserCrudAppApplication>(*args)
}
