package com.example.ghandapp.usuario.login.data.remote

import com.example.ghandapp.usuario.login.data.local.UserRequest
import com.example.ghandapp.usuario.login.data.remote.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @GET()
    suspend fun getLog(@Query("username") username: String,
                       @Query("password") password: String): Response<LoginResponse>

    @POST("/createUser")
    suspend fun createUser(@Body userRequest: UserRequest): Response<ResponseBody>

    @GET
    suspend fun getUser(@Query("username") username: String):Response<LoginResponse>
}