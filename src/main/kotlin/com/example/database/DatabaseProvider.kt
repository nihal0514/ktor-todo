package com.example.database

import com.example.data.TodoDataSource
import com.example.data.TodoDataSourceImpl
import com.example.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule= module{
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("todo_db")

    }
    single<TodoDataSource> {
        TodoDataSourceImpl(get())
    }
    single{
        RoomController(get())
    }
}