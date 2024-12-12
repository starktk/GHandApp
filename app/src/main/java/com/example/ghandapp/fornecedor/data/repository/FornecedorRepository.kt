package com.example.ghandapp.fornecedor.data.repository

import android.util.Log
import android.view.View
import com.example.ghandapp.database.FHdatabase
import com.example.ghandapp.databinding.ActivityFornecedorBinding
import com.example.ghandapp.fornecedor.data.local.FornecedorEntity
import com.example.ghandapp.fornecedor.data.remote.FornecedorRequest
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.data.remote.FornecedorResponse
import com.example.ghandapp.fornecedor.data.remote.FornecedorService
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.network.RetrofitNetworkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class FornecedorRepository {

    private lateinit var bindingFornecedor: ActivityFornecedorBinding

    private val database: FHdatabase by lazy {
        FHdatabase.getInstance()
    }

    private val client =
        RetrofitNetworkClient
            .createNetworkClient()
            .create(FornecedorService::class.java)

    suspend fun createFornecedor(razaoSocial: String, cnpj: String, username: String, name: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.createFornecedor(FornecedorRequest(razaoSocial, cnpj, status = Situacao.ATIVA , username, name = name))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
    suspend fun findFornecedorByCnpj(username: String, cnpj: String): FornecedorModel? {
        return withContext(Dispatchers.IO) {
            try {
                val fornecedorFind = client.findFornecedorByCnpj(FornecedorRequest(username = username, cnpj = cnpj))
                if (fornecedorFind.isSuccessful) {
                    fornecedorFind.body().fornecedorResponseToFornecedorModel()
                } else {
                    null
                }
            } catch (exception: Exception) {
               Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                null
            }
        }
    }

    suspend fun cleanDBFornecedor() {
        return withContext(Dispatchers.IO) {
            database.fornecedorDao().cleanFornecedor()
        }
    }

    suspend fun getAllFornecedores(username: String, contextView: View): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val fornecedoresReturn = client.getAllFornecedores(username = username)
                if(fornecedoresReturn.isSuccessful) {
                    println(fornecedoresReturn.body())
                    cleanDBFornecedor()
                    saveFornecedorInDb(username,fornecedoresReturn.body()?.mapperFornecedor() ?: emptyList())
                    fornecedoresReturn.body()?.mapperFornecedor() ?: emptyList()

                } else {
                    println("Erro na requisição: Código ${fornecedoresReturn.code()} - ${fornecedoresReturn.message()}")
                    emptyList()
                }
            } catch (exception: Exception) {
               Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
    }

    suspend fun getFornecedoresInDb(username: String): List<FornecedorModel> {
        return try {
            database.fornecedorDao().getFornecedor(username).fornecedores ?: emptyList()
        } catch (exception: Exception) {
            println("Sem cache para mostrar")
            emptyList()
        }
    }
    suspend fun alterFornecedor(username: String?, cnpjUpdated: String?, razaoSocial: String?, status: Situacao?, cnpj: String?, name: String?, contextView: View): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.alterFornecedor(cnpj = cnpj, FornecedorRequest(razaoSocial = razaoSocial, cnpj = cnpjUpdated, status = status, username = username, name = name))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }

    suspend fun saveFornecedorInDb(username: String, fornecedores: List<FornecedorModel>) {
        return withContext(Dispatchers.IO){
            try {
                val fornecedorEntity = FornecedorEntity(username, fornecedores)
                database.fornecedorDao().insertAllFornecedores(fornecedorEntity)
            } catch (exception: Exception) {
                Log.e("DB Error", "Erro ao salvar fornecedores no banco local: ${exception.message}")
            }
        }
    }

    suspend fun modifyStatus(username: String, name: String, cnpj: String, status: Situacao): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.alterStatus(FornecedorRequest(username = username, cnpj = cnpj, status = status, name = name))
                response.isSuccessful
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                false
            }
        }
    }
    suspend fun deleteFornecedor(username: String, cnpj: String): Boolean {
        return withContext(Dispatchers.IO) {
           try {
               val response = client.deleteFornecedor(FornecedorRequest(username = username, cnpj = cnpj))
               response.isSuccessful
           } catch (exception: Exception) {
               Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
               false
           }
        }
    }

    suspend fun findByRazaoSocial(username: String, razaoSocial: String, contextView: View): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findFornecedoresByRazaoSocial(FornecedorRequest(razaoSocial = razaoSocial, username = username))
                if (response.isSuccessful || response.code() == 302) {
                    response.body()?.mapperFornecedor() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Snackbar.make(contextView, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
    }
    suspend fun findByStatus(username: String, status: Situacao): List<FornecedorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.findByStatus(FornecedorRequest(username = username , status = status))
                if (response.isSuccessful || response.code() == 302) {
                    response.body()?.mapperFornecedor() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (exception: Exception) {
                Snackbar.make(bindingFornecedor.root, exception.message.toString(), Snackbar.LENGTH_SHORT).show()
                emptyList()
            }
        }
    }



    private fun List<FornecedorResponse>.mapperFornecedor(): List<FornecedorModel> {
        return map {
            it.fornecedorResponseToFornecedorModel()
        }
    }


    private fun FornecedorResponse?.fornecedorResponseToFornecedorModel(): FornecedorModel {
        return FornecedorModel(
            razaoSocial = this?.razaoSocial,
            cnpj = this?.cnpj,
            status = this?.status
        )
    }
}


