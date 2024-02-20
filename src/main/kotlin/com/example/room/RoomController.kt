package com.example.room

import com.example.data.TodoDataSource
import com.example.data.model.auth.user.User
import com.example.data.model.todo.Todo

class RoomController(
    private val todoDataSource: TodoDataSource
){
    suspend fun registerUser(user: User){
        todoDataSource.registerUser(user)
    }
    suspend fun loginUser(user: User): Boolean{
        return todoDataSource.loginUser(user)

    }
    suspend fun getUserData(username: String): User {
        return todoDataSource.getUserData(username)
    }
    suspend fun setTodo(userId: String,todo: Todo){
        todoDataSource.setTodo(userId,todo)
    }
    suspend fun getTodo(userId: String):List<Todo> {
        return todoDataSource.getTodo(userId)
    }
    suspend fun updateTodo(_id: String,todo: Todo){
         todoDataSource.updateTodo(_id,todo)
    }
    suspend fun deleteTodo(_id: String){
        todoDataSource.deleteTodo(_id)
    }
}