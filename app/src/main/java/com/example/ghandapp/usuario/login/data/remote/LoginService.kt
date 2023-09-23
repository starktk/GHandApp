package com.example.ghandapp.usuario.login.data.remote

import com.example.ghandapp.usuario.login.data.local.UserRequest
import com.example.ghandapp.usuario.login.data.remote.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LoginService {

    @GET("usuario")
    suspend fun getLog(@Query("username") username: String,
                       @Query("password") password: String): Response<LoginResponse>

    @POST("usuario/createUser")
    suspend fun createUser(@Body userRequest: UserRequest): Response<ResponseBody>

    @GET("usuario/getUser/{username}")
    suspend fun findUser(@Query("username") username: String):Response<LoginResponse>

    @PUT("usuario/alterUser")
    suspend fun alterUser(@Body loginResponse: LoginResponse): Response<LoginResponse>

    @DELETE("usuario/deleteUser/{username}")
    suspend fun deleteUser(@Query("username") username: String): Response<ResponseBody>
}