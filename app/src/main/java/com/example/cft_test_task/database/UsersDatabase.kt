package com.example.cft_test_task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cft_test_task.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun usersDao(): UsersDao
}