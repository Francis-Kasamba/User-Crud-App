package com.example.usercrudapp.service

import com.example.usercrudapp.model.User
import com.example.usercrudapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun getAllUsers(): List<User> = userRepository.findAll()

    override fun getUserById(id: Long): User? = userRepository.findById(id).orElse(null)

    override fun createUser(user: User): User = userRepository.save(user)

    override fun updateUser(id: Long, user: User): User? {
        return if (userRepository.existsById(id)) {
            userRepository.save(user.copy(id = id))
        } else {
            null
        }
    }

    override fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}