package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.boot.
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean

@SpringBootApplication//(scanBasePackages=["com.example.demo.repository"])
class UserServiceMongodbApplication

fun main(args: Array<String>) {
	runApplication<UserServiceMongodbApplication>(*args)
    
     @Bean
    fun runnerProbe(): CommandLineRunner = CommandLineRunner {
        println(">>> RUNNER PROBE EXECUTED <<<")
    }
}
