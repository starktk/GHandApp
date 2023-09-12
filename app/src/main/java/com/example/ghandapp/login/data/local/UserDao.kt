package com.example.ghandapp.login.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertProfile(userEntity: UserEntity)

    @Query("SELECT name FROM userTable")
    fun getName(): String
}