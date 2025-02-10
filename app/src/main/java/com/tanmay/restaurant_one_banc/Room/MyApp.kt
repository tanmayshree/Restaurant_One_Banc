package com.tanmay.restaurant_one_banc.Room

import android.app.Application

class MyApp : Application() {

    lateinit var repository: RoomRepository

    override fun onCreate() {
        super.onCreate()
        val database by lazy { AppDatabase.getDatabase(this) }
//        repository by lazy { RoomRepository(database.cuisineDao()) }
        repository = RoomRepository(database.cuisineDao())
    }
}
