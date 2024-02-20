package com.example.data

import com.example.data.model.auth.user.User
import com.example.data.model.todo.Todo
import com.example.utils.Hashing
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import org.litote.kmongo.coroutine.CoroutineDatabase

class TodoDataSourceImpl(
    private val db: CoroutineDatabase
): TodoDataSource{
    private val users= db.getCollection<User>()
    private val todos= db.getCollection<Todo>()
    override suspend fun registerUser(user: User) {
        users.insertOne(user)
    }

    override suspend fun loginUser(user: User) : Boolean{
       var userNameExists= users.findOne(eq("username", user.username))

        if(userNameExists!=null && Hashing.verifyPassword(user.password,userNameExists.password)){
            print("userNameExists"+userNameExists?._id)
            return true
        }
        return false
    }

    override suspend fun getUserData(username: String) : User {
        var user= users.findOne(eq("username",username))
        return user!!
    }

    override suspend fun setTodo(userId: String,todo: Todo) {
        todo.userId= userId
        todos.insertOne(todo)
    }

    override suspend fun getTodo(userId: String): List<Todo> {
        var todos= todos.find(eq("userId",userId)).toList()
       return todos
    }

    override suspend fun updateTodo(_id: String,newTodo: Todo) {
        todos.updateOne(
            Filters.eq("_id", _id),
            Updates.combine(
                Updates.set("title", newTodo.title),
                Updates.set("description", newTodo.description)
            )
        )
    }

    override suspend fun deleteTodo(_id: String) {
        todos.deleteOne(Filters.eq("_id",_id))
    }

}