package com.example.ghandapp.fornecedor.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface FornecedorService {

    @POST("/fornecedor/findFornecedorByCnpj")
    suspend fun findFornecedorByCnpj(@Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>
    @GET("/fornecedor/findFornecedorByRazaoSocial")
    suspend fun findFornecedoresByRazaoSocial(@Body fornecedorRequest: FornecedorRequest): Response<List<FornecedorResponse>>
    @POST("/fornecedor/createFornecedor")
    suspend fun createFornecedor(@Body fornecedorRequest: FornecedorRequest): Response<ResponseBody>

    @PUT("/fornecedor/updateFornecedor")
    suspend fun alterFornecedor(@Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>

    @DELETE("/fornecedor/deleteFornecedor")
    suspend fun deleteFornecedor(@Body fornecedorRequest: FornecedorRequest): Response<ResponseBody>

    @POST("/fornecedor/findAllFornecedores/{id}")
    suspend fun getAllFornecedores(@Query("id") username: String): Response<List<FornecedorResponse>>

    @PUT("/fornecedor/alterStatus")
    suspend fun alterStatus(@Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>

    @GET("fornecedor/findFornecedorByStatus")
    suspend fun findByStatus(@Body fornecedorRequest: FornecedorRequest): Response<List<FornecedorResponse>>

    @PUT("fornecedor/updateStatus")
    suspend fun updateByStatus(@Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>
}