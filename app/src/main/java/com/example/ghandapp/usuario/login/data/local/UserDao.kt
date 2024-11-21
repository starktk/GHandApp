package com.example.ghandapp.usuario.login.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
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

    @Query("SELECT * FROM userTable")
    fun getUser(): UserEntity

    @Query("DELETE FROM userTable")
    fun cleanCache()
}