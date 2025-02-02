package com.example.ghandapp.usuario.login.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class UserEntity (
    @PrimaryKey
    val username: String,
    val name: String
        )