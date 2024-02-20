package com.example.data

import com.example.data.model.auth.user.User
import com.example.data.model.todo.Todo

interface TodoDataSource {
    suspend fun registerUser(user: User)

    suspend fun loginUser(user: User): Boolean

    suspend fun getUserData(username: String): User

    suspend fun setTodo(userId: String,todo: Todo)

    suspend fun getTodo(userId: String): List<Todo>

    suspend fun updateTodo(_id: String,todo: Todo)

    suspend fun deleteTodo(_id: String)
}