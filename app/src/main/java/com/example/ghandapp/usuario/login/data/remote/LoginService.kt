package com.example.ghandapp.usuario.login.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LoginService {

    @POST("/usuario/logUser")
    suspend fun getLog(@Body userRequest: UserRequest): Response<LoginResponse>

    @POST("/usuario/create")
    suspend fun createUser(@Body userRequest: UserRequest): Response<ResponseBody>

    @GET("/usuario/findUserByid/{username}")
    suspend fun findUser(@Query("username") username: String):Response<LoginResponse>

    @PUT("usuario/modifyUserInfos")
    suspend fun alterUser(@Query("username")username: String,
                          @Query("usernameToSet")usernameToSet: String,
                          @Query("nameToSet")nameToSet: String,
                          @Query("password")password: String): Response<LoginResponse>

    @DELETE("usuario/deleteUser/{username}")
    suspend fun deleteUser(@Query("username") username: String): Response<ResponseBody>
}