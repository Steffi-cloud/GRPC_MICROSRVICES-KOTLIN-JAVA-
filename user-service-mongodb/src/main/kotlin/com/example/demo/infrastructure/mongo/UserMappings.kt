package com.example.demo.infrastructure.mongo

import com.example.demo.model.User
import com.example.demo.model.UserStatus

fun UserDocument.toDomain(): User =
    User(
        id = id,
        name = name,
        email = email,
        status = UserStatus.valueOf(status)
    )

fun User.toDocument(): UserDocument =
    UserDocument(
        id = id,
        name = name,
        email = email,
        status = status.name
    )
