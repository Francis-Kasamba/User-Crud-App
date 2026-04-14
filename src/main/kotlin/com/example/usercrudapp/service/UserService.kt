package com.example.usercrudapp.service

import com.example.usercrudapp.dto.UserResponse
import com.example.usercrudapp.model.User

interface UserService {
    fun getAllUsers(): List<UserResponse>

    fun getUserById(id: Long): User?

    fun createUser(user: User): User

    fun updateUser(id: Long, user: User): User?

    fun deleteUser(id: Long): Boolean
}