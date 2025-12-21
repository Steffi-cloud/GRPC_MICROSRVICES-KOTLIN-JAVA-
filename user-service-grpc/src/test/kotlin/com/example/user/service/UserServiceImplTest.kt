package com.example.user.service

import com.example.user.v1.GetUserProfileRequest
import com.example.user.v1.GetUserProfileResponse
import io.grpc.stub.StreamObserver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.time.Duration
import kotlin.test.assertEquals

class UserServiceImplTest {

    private lateinit var redisTemplate: RedisTemplate<String, ByteArray>
    private lateinit var valueOps: ValueOperations<String, ByteArray>
    private lateinit var responseObserver: StreamObserver<GetUserProfileResponse>

    private lateinit var service: UserServiceImpl

    @BeforeEach
    fun setUp() {
        redisTemplate = mock()
        valueOps = mock()
        responseObserver = mock()

        whenever(redisTemplate.opsForValue()).thenReturn(valueOps)

        service = UserServiceImpl(redisTemplate)
    }

    // ----------------------------
    // 1️⃣ Cache HIT
    // ----------------------------
    @Test
    fun `should return cached response when Redis HIT occurs`() {
        val request = GetUserProfileRequest.newBuilder()
            .setUserId("123")
            .build()

        val cachedResponse = GetUserProfileResponse.newBuilder()
            .setUserId("123")
            .setName("Cached User")
            .setEmail("cached@example.com")
            .setUpdatedAt(1000L)
            .build()

        whenever(valueOps.get("user:123"))
            .thenReturn(cachedResponse.toByteArray())

        service.getUserProfile(request, responseObserver)

        val captor = argumentCaptor<GetUserProfileResponse>()
        verify(responseObserver).onNext(captor.capture())
        verify(responseObserver).onCompleted()

        val response = captor.firstValue
        assertEquals("123", response.userId)
        assertEquals("Cached User", response.name)

        verify(valueOps, never()).set(any(), any(), any<Duration>())
    }

    // ----------------------------
    // 2️⃣ Cache MISS
    // ----------------------------
    @Test
    fun `should build response and store in Redis when cache MISS occurs`() {
        val request = GetUserProfileRequest.newBuilder()
            .setUserId("456")
            .build()

        whenever(valueOps.get("user:456")).thenReturn(null)

        service.getUserProfile(request, responseObserver)

        val captor = argumentCaptor<GetUserProfileResponse>()
        verify(responseObserver).onNext(captor.capture())
        verify(responseObserver).onCompleted()

        val response = captor.firstValue
        assertEquals("456", response.userId)
        assertEquals("John Doe", response.name)

        verify(valueOps).set(
            eq("user:456"),
            any(),
            eq(Duration.ofSeconds(60))
        )
    }

    // ----------------------------
    // 3️⃣ Redis FAILURE
    // ----------------------------
    @Test
    fun `should fallback gracefully when Redis throws exception`() {
        val request = GetUserProfileRequest.newBuilder()
            .setUserId("789")
            .build()

        whenever(redisTemplate.opsForValue())
            .thenThrow(RuntimeException("Redis down"))

        service.getUserProfile(request, responseObserver)

        val captor = argumentCaptor<GetUserProfileResponse>()
        verify(responseObserver).onNext(captor.capture())
        verify(responseObserver).onCompleted()

        val response = captor.firstValue
        assertEquals("789", response.userId)
        assertEquals("John Doe", response.name)
    }
}
