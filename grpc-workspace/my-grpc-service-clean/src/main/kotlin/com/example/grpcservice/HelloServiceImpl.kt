package com.example.grpcservice

import com.example.grpcservice.HelloRequest
import com.example.grpcservice.HelloReply
import com.example.grpcservice.HelloServiceGrpcKt

import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class HelloServiceImpl : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {

    override
    suspend fun sayHello(request: HelloRequest): HelloReply {
        return  HelloReply.newBuilder().setMessage("Hello, ${request.name}").build()
    }
}
  
  
 