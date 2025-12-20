package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.grpcservice"])
class MyGrpcServiceApplication {
    // your beans and code here, commented or uncommented
}

// top-level main function outside class
fun main(args: Array<String>) {
    runApplication<MyGrpcServiceApplication>(*args)
}
