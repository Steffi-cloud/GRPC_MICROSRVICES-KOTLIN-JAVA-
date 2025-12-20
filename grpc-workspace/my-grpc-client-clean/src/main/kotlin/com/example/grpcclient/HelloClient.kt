package com.example.grpcclient

import com.example.grpcservice.HelloRequest
import com.example.grpcservice.HelloServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import io.grpc.StatusException

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main() = runBlocking {
    println("🔥 REAL ENTRYPOINT: HelloClientKt 🔥")

    val host = System.getenv("GRPC_HOST") ?: "localhost"
    val port = (System.getenv("GRPC_PORT") ?: "9090").toInt()

    val channel = ManagedChannelBuilder
        .forAddress(host, port)
        .usePlaintext()
        .build()

  //  val stub = HelloServiceGrpcKt.HelloServiceCoroutineStub(channel)
   //     .withDeadlineAfter(5, TimeUnit.SECONDS)

    val request = HelloRequest.newBuilder()
        .setName("Kotlin Client")
        .build()

    println("Client connecting to $host:$port")

  repeat(10) { attempt ->
    try {
        println("Attempt ${attempt + 1}")

        val stubWithDeadline =
            HelloServiceGrpcKt.HelloServiceCoroutineStub(channel)
                .withDeadlineAfter(5, TimeUnit.SECONDS)

        val response = stubWithDeadline.sayHello(request)

        println("Response from server: ${response.message}")
        channel.shutdown()
        return@runBlocking

    } catch (e: StatusException) {
        println("Attempt ${attempt + 1} failed: ${e.status}")
        delay(1000)
    }
}


    println("Server never became ready")
    channel.shutdown()
}
