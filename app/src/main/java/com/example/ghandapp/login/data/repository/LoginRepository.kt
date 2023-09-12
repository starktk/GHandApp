package com.example.ghandapp.login.data.repository

import android.util.Log
import com.example.ghandapp.database.FHdatabase
import com.example.ghandapp.login.data.local.UserEntity
import com.example.ghandapp.login.data.local.UserRequest
import com.example.ghandapp.login.data.remote.LoginResponse
import com.example.ghandapp.network.RetrofitNetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository {

    private val database: FHdatabase by lazy {
        FHdatabase.getInstance()
    }

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(LoginService::class.java)

    suspend fun logar(username: String, password: String): Boolean {

        return withContext(Dispatchers.IO) {
            try {
                val response = client.getLog(username, password)
                saveUser(response)
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("login", exception.message.orEmpty())
                false
            }
        }
    }
    suspend fun createUser(userRequest: UserRequest): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.createUser()

            }
        }
    }

    private suspend fun saveUser(user: Response<LoginResponse>) {
        return withContext(Dispatchers.IO) {
            if (user.isSuccessful) {
                user.body()?.run {
                    database.userDao().insertProfile(
                        userResponseToEntity()
                    )
                }
            }
        }
    }

    private fun LoginResponse.userResponseToEntity(): UserEntity {
        return UserEntity(
            username = username,
            name = name
        )
    }
}