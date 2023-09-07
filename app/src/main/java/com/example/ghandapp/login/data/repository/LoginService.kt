package com.example.ghandapp.login.data.repository

import com.example.ghandapp.login.data.model.UserRequest
import com.example.ghandapp.login.data.remote.LoginResponse
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
    suspend fun createUser(@Body userRequest: UserRequest)
}