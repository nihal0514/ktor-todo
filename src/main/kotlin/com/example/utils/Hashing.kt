package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.mindrot.jbcrypt.BCrypt
import java.util.Date

class Hashing {
     companion object{
         fun hashPassword(password: String): String{
             return BCrypt.hashpw(password,BCrypt.gensalt())
         }

         fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean{
             return BCrypt.checkpw(plainPassword,hashedPassword)
         }
         object JwtConfig {
             val secretKey = "your_secret_key"
             val algorithm = Algorithm.HMAC256(secretKey)

             val verifier = JWT.require(algorithm)
                 .withIssuer("ktor-todo")
                 .build()

             fun makeToken(username: String): String = JWT.create()
                 .withSubject("Authentication")
                 .withIssuer("ktor-todo")
                 .withClaim("username", username)
                 .withExpiresAt(Date(System.currentTimeMillis() + 600000)) // Token expiration time
                 .sign(algorithm)
         }

     }

}