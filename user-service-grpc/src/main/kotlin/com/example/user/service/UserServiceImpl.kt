package com.example.user.service

import com.example.user.v1.GetUserProfileRequest
import com.example.user.v1.GetUserProfileResponse
import com.example.user.v1.UserServiceGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

@GrpcService
class UserServiceImpl(
    private val redisTemplate: RedisTemplate<String, ByteArray>
) : UserServiceGrpc.UserServiceImplBase() {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun getUserProfile(
        request: GetUserProfileRequest,
        responseObserver: StreamObserver<GetUserProfileResponse>
    ) {
        val key = "user:${request.userId}"

        try {
            // 1️⃣ Try Redis
            val cached = redisTemplate.opsForValue().get(key)
            if (cached != null) {
                log.info("Cache HIT for {}", key)
                val response = GetUserProfileResponse.parseFrom(cached)
                responseObserver.onNext(response)
                responseObserver.onCompleted()
                return
            }

            log.info("Cache MISS for {}", key)

            // 2️⃣ Build response (mock for now)
            val response = GetUserProfileResponse.newBuilder()
                .setUserId(request.userId)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setUpdatedAt(System.currentTimeMillis())
                .build()

            // 3️⃣ Store in Redis with TTL
            redisTemplate.opsForValue()
                .set(key, response.toByteArray(), Duration.ofSeconds(60))

            responseObserver.onNext(response)
            responseObserver.onCompleted()

        } catch (ex: Exception) {
            log.warn("Redis error, falling back", ex)

            // Fallback: still return response
            val response = GetUserProfileResponse.newBuilder()
                .setUserId(request.userId)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .setUpdatedAt(System.currentTimeMillis())
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()
        }
    }
}
