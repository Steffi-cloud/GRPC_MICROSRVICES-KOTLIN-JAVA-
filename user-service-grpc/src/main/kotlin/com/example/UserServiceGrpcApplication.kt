package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserServiceGrpcApplication

fun main(args: Array<String>) {
    runApplication<UserServiceGrpcApplication>(*args)
}
