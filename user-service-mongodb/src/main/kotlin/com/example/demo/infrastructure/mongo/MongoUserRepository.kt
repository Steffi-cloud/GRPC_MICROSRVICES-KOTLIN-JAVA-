package com.example.demo.infrastructure.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface MongoUserRepository :
    MongoRepository<UserDocument, String>
