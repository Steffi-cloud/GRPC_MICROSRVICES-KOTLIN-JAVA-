package com.example.demo.api

import com.example.demo.model.User
import com.example.demo.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody user: User): User =
        userService.create(user)

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): User =
        userService.get(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody user: User
    ): User =
        userService.update(user.copy(id = id))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        userService.delete(id)
    }
}
