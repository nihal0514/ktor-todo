package com.example.data.model.todo
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId


@Serializable
data class Todo(

    val _id: String= ObjectId().toString(),
    var userId: String= ObjectId().toString(),
    val title: String,
    val description: String,
)