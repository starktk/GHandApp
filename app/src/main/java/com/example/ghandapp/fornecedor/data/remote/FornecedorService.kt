package com.example.ghandapp.fornecedor.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FornecedorService {

    @POST("/fornecedor/findFornecedorByCnpj")
    suspend fun findFornecedorByCnpj(@Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>
    @POST("/fornecedor/findFornecedorByRazaoSocial")
    suspend fun findFornecedoresByRazaoSocial(@Body fornecedorRequest: FornecedorRequest): Response<List<FornecedorResponse>>
    @POST("/fornecedor/createFornecedor")
    suspend fun createFornecedor(@Body fornecedorRequest: FornecedorRequest): Response<ResponseBody>

    @PUT("/fornecedor/updateFornecedor/{cnpj}")
    suspend fun alterFornecedor(@Path("cnpj") cnpj: String?, @Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>

    @Headers("Content-Type: application/json")
    @DELETE("/fornecedor/deleteFornecedor")
    suspend fun deleteFornecedor(    @Query("username") username: String?,
                                     @Query("cnpj") cnpj: String?): Response<ResponseBody>

    @POST("/fornecedor/findAllFornecedores/{id}")
    suspend fun getAllFornecedores(@Path("id") username: String): Response<List<FornecedorResponse>>

    @PUT("/fornecedor/updateStatus")
    suspend fun alterStatus(@Body fornecedorRequest: FornecedorRequest): Response<FornecedorResponse>

    @GET("fornecedor/findFornecedorByStatus")
    suspend fun findByStatus(@Body fornecedorRequest: FornecedorRequest): Response<List<FornecedorResponse>>


}