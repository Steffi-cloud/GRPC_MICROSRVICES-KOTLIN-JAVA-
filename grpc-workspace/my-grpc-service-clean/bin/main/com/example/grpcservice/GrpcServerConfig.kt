/*
 * package com.example.grpcservice
 * 
 * 
 * import net.devh.boot.grpc.server.server.GrpcServerLifecycle import
 * org.springframework.context.annotation.Bean import
 * org.springframework.context.annotation.Configuration
 * 
 * @Configuration class GrpcServerConfig {
 * 
 * @Bean fun grpcServerRunner(lifecycle: GrpcServerLifecycle) =
 * ApplicationRunner { lifecycle.start()
 * println("✅ gRPC server started on port: ${lifecycle.port}") } }
 * 
 * 
 */