package com.example.data.model.auth.register

import com.example.data.model.auth.user.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    var token: String ?= null,
    val status: Boolean,val user: User
)
