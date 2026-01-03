package com.example.demo.infrastructure.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class UserDocument(
    @Id
    val id: String,
    val name: String,
    val email: String,
    val status: String
)
