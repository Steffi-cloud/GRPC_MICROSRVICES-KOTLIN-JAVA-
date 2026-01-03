package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun create(user: User): User {
        return userRepository.save(user)
    }

    fun update(user: User): User {
        return userRepository.save(user)
    }

    fun get(id: String): User {
        return userRepository.findById(id)
            ?: throw IllegalArgumentException("User not found: $id")
    }

    fun delete(id: String) {
        userRepository.deleteById(id)
    }
}
