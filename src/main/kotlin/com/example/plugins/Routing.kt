package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.room.RoomController
import com.example.routes.authenticateRoutes
import com.example.routes.todoRoutes
import com.example.utils.Hashing
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.routing.routing
import org.koin.java.KoinJavaComponent.inject
import java.util.Date


fun Application.configureRouting() {

    val roomController: RoomController by inject<RoomController>(RoomController::class.java)

    install(Authentication) {
        jwt("jwt") {
            verifier(Hashing.Companion.JwtConfig.verifier)
            validate {
                UserIdPrincipal(it.payload.getClaim("username").asString())
            }
        }
    }
    routing {
        authenticateRoutes(roomController)
        todoRoutes(roomController)

    }
}

