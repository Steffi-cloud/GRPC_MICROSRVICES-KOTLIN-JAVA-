package com.example.demo.repository

import com.example.demo.model.User

interface UserRepository {
     fun findById(userId: String): User?
   fun save(user: User): User

     fun deleteById(userId: String)
}