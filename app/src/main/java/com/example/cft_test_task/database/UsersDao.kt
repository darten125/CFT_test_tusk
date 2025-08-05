package com.example.cft_test_task.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cft_test_task.database.entities.UserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users")
    suspend fun getSavedUsers(): List<UserEntity>

    @Query("DELETE FROM users")
    suspend fun clearSavedUsers()

    @Transaction
    suspend fun replaceAll(users: List<UserEntity>) {
        clearSavedUsers()
        saveUsers(users)
    }
}