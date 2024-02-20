package com.example.data.model.auth.login

import com.example.data.model.auth.user.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    var token: String ?= null,
    val status: Boolean,
    val user: User
)
