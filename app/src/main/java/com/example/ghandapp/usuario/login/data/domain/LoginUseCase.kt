package com.example.ghandapp.usuario.login.data.domain

import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class LoginUseCase {

    private val repository by lazy { LoginRepository() }

    suspend fun login(username: String, password: String): Boolean {
        return repository.logar(username = username, password = password)
    }

    suspend fun createUser(username: String, name: String ,password: String): Boolean {
        return repository.createUser(username, name, password)
    }

    suspend fun getUser(): UserEntity {
        return repository.getUser()
    }

    suspend fun validateLog(): Boolean {
        val userEntity: UserEntity = getUser()
        if (userEntity.username.isNullOrBlank()) {
            return false
        }
        return true
    }
}