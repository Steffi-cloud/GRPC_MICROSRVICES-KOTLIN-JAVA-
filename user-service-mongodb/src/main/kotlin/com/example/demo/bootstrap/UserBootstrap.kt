package com.example.demo.bootstrap

import com.example.demo.model.User
import com.example.demo.model.UserStatus
import com.example.demo.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.UUID

@Configuration
class UserBootstrap {

    private val log = LoggerFactory.getLogger(UserBootstrap::class.java)

    @Bean
    fun userRunner(userService: UserService): CommandLineRunner {
        return CommandLineRunner {

            log.info("=== BOOTSTRAP: Starting User persistence test ===")

            // 1️⃣ Insert a user
            val userId = UUID.randomUUID().toString()

            val user = User(
                id = userId,
                name = "Steffi Das",
                email = "steffi.das@example.com",
                status = UserStatus.ACTIVE
            )

            val savedUser = userService.create(user)
            log.info("User saved: {}", savedUser)

            // 2️⃣ Read it back
            val fetchedUser = userService.get(userId)
            log.info("User fetched: {}", fetchedUser)

            log.info("=== BOOTSTRAP: User persistence test completed ===")
        }
    }
}
