package com.example.cft_test_task

import android.app.Application
import androidx.room.Room
import com.example.cft_test_task.database.UsersDatabase

class UsersListApp: Application() {
    companion object {
        lateinit var database: UsersDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            UsersDatabase::class.java,
            "users.db"
        ).build()
    }
}