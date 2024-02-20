package com.example.data.model.auth.user

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class User(

    val _id: String= ObjectId().toString(),
    val username: String,
    val email: String,
    var password: String

)