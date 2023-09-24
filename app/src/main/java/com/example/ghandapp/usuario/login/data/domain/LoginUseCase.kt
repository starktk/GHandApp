package com.example.ghandapp.usuario.login.data.domain

import com.example.ghandapp.usuario.login.data.repository.LoginRepository

class LoginUseCase {

    private val repository by lazy { LoginRepository() }

    suspend fun login(username: String, password: String): Boolean {
        return repository.logar(username = username, password = password)
    }

    suspend fun createUser(username: String, name: String ,password: String): Boolean {
        return repository.createUser(username, name, password)
    }

    suspend fun findUser(username: String) {
        return repository.findUser(username)
    }

    suspend fun getUsername(): String {
        return repository.getusername()
    }

}