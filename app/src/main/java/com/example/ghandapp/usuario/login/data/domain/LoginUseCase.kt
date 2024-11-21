package com.example.ghandapp.usuario.login.data.domain

import android.view.View
import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class LoginUseCase {

    private val repository by lazy { LoginRepository() }

    suspend fun login(username: String, password: String, contextView: View): Boolean {
        return repository.logar(username, password, contextView)
    }

    suspend fun createUser(username: String, name: String ,password: String): Boolean {
        return repository.createUser(username, name, password)
    }
    suspend fun getUsername(): String {
        val username = repository.getUsername()
        return username
    }
    suspend fun getUser(): UserEntity {
        return repository.getUser()
    }

}