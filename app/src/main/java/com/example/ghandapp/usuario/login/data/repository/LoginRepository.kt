package com.example.ghandapp.usuario.login.data.repository

import android.util.Log
import com.example.ghandapp.database.FHdatabase
import com.example.ghandapp.usuario.login.data.local.UserEntity
import com.example.ghandapp.usuario.login.data.local.UserRequest
import com.example.ghandapp.usuario.login.data.remote.LoginResponse
import com.example.ghandapp.network.RetrofitNetworkClient
import com.example.ghandapp.usuario.login.data.remote.LoginService
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
    suspend fun createUser(username: String, name: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.createUser(UserRequest(username, name, password))
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("create", exception.message.orEmpty())
                false
            }
        }
    }

    suspend fun findUser(username: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.getUser(username = username)
                response.isSuccessful
            } catch (exception: Exception) {
                Log.e("findUSer", exception.message.orEmpty())
                false
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