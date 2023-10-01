package com.example.ghandapp.usuario.login.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertProfile(userEntity: UserEntity)

    @Query("SELECT name FROM userTable")
    fun getName(): String

    @Query("SELECT username FROM userTable")
    fun getUsername(): String


}