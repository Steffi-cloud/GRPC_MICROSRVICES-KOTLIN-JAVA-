package com.example.demo.infrastructure.mongo

import com.example.demo.model.User
import com.example.demo.model.UserStatus
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    private val mongoUserRepository: MongoUserRepository
) : UserRepository {

override fun findById(userId: String): User? {
    val optional = mongoUserRepository.findById(userId)
    return optional.map { it.toDomain() }.orElse(null)
}



    override fun save(user: User): User =
        mongoUserRepository.save(user.toDocument())
            .toDomain()

    override fun deleteById(userId: String) {
        mongoUserRepository.deleteById(userId)
    }
}
