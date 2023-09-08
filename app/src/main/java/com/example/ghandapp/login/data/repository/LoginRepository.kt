package com.example.ghandapp.login.data.repository

import com.example.ghandapp.network.RetrofitNetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(LoginService::class.java)

    suspend fun logar(username: String, password: String): Boolean {

        return withContext(Dispatchers.IO) {
            try {
                val response = client.getLog(username, password)

            }
        }
    }


    private suspend fun svaeUser(): Boolean {

    }
}