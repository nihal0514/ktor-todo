package com.example.routes

import com.example.data.model.auth.user.User
import com.example.data.model.auth.register.RegisterResponse
import com.example.data.model.todo.Todo
import com.example.room.RoomController
import com.example.utils.Hashing
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put


fun Route.authenticateRoutes(roomController: RoomController) {
    post("/auth/register") {
        var user = call.receive<User>()
        var newPassword= Hashing.hashPassword(user.password)
        user.password= newPassword

        roomController.registerUser(user)
        val token = Hashing.Companion.JwtConfig.makeToken(user.username)
        val response = RegisterResponse(token = token, status = true, user = user)

        call.respond(
            HttpStatusCode.OK,
            response
        )
    }

    post("/auth/login"){
        var user = call.receive<User>()

        var isLogin= roomController.loginUser(user)
        val token = Hashing.Companion.JwtConfig.makeToken(user.username)
        val response = RegisterResponse(token = token, status = true, user = user)
        if(isLogin){
            call.respond(
                HttpStatusCode.OK,
                response
            )
        }else{
            call.respond(
                HttpStatusCode.NonAuthoritativeInformation,
                "Please enter correct details"
            )
        }

    }

    authenticate("jwt"){
        get("/auth/user"){
            val principal = call.authentication.principal<UserIdPrincipal>()
            var userData= roomController.getUserData(principal?.name.toString())
            call.respond(
                HttpStatusCode.OK,
                userData
            )
        }

    }

    authenticate("jwt") {
        post ("/todo/settodo"){
            var todo = call.receive<Todo>()

            val principal = call.authentication.principal<UserIdPrincipal>()
            var userData= roomController.getUserData(principal?.name.toString())

            roomController.setTodo(userData._id,todo)

            call.respond(
                HttpStatusCode.OK,
                todo
            )
        }
    }


}
fun Route.todoRoutes(roomController: RoomController) {
    authenticate("jwt"){
        get("/todo/get"){

            val principal = call.authentication.principal<UserIdPrincipal>()
            var userData= roomController.getUserData(principal?.name.toString())

            var todos= roomController.getTodo(userData._id)

            call.respond(
                HttpStatusCode.OK,
                todos
            )
        }
    }

    authenticate("jwt") {
        put("/todo/update/{id}") {
            val id = call.parameters["id"] ?: ""
            var todo = call.receive<Todo>()
            roomController.updateTodo(id,todo)

            call.respond(
                HttpStatusCode.OK,
                "Ok"
            )
        }
    }

    authenticate("jwt") {
        delete("/todo/delete/{id}") {
            val id = call.parameters["id"] ?: ""
            roomController.deleteTodo(id)
            call.respond(
                HttpStatusCode.OK,
                "Ok"
            )
        }
    }
}