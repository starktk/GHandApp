package com.example.ghandapp.fornecedor.data.domain

import android.view.View
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.data.repository.FornecedorRepository
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase

class FornecedorUseCase {

    private val repositoryFornecedor by lazy { FornecedorRepository() }

    private val loginUseCase by lazy {
        LoginUseCase()
    }
    suspend fun createFornecedor(razaoSocial: String, cnpj: String,): Boolean {
        return repositoryFornecedor.createFornecedor(razaoSocial, cnpj, loginUseCase.getUser().username, loginUseCase.getUser().name)
    }

    suspend fun findFornecedorByCnpj(cnpj: String): FornecedorModel? {
        return repositoryFornecedor.findFornecedorByCnpj(loginUseCase.getUser().username, cnpj)
    }

    suspend fun deleteFornecedor(cnpj: String): Boolean {
        return repositoryFornecedor.deleteFornecedor(loginUseCase.getUser().username, cnpj)
    }

    suspend fun modifyStatus(cnpj: String,status: Situacao): Boolean {
        return repositoryFornecedor.modifyStatus(loginUseCase.getUser().username, loginUseCase.getUser().name, cnpj, status)
    }

    suspend fun getAllFornecedores(contextView: View): List<FornecedorModel> {
        return repositoryFornecedor.getAllFornecedores(loginUseCase.getUsername(), contextView)
    }

    suspend fun findFornecedoresByRazaoSocial(razaoSocial: String, contextView: View): List<FornecedorModel> {
        return repositoryFornecedor.findByRazaoSocial(loginUseCase.getUser().username, razaoSocial, contextView)
    }

    suspend fun findFornecedoresByStatus(status: Situacao): List<FornecedorModel> {
        return repositoryFornecedor.findByStatus(loginUseCase.getUser().username, status)
    }
    suspend fun getAllFornecedoresInCache(): List<FornecedorModel>{
        return repositoryFornecedor.getFornecedoresInDb(loginUseCase.getUsername())
    }

    suspend fun alterFornecedor(razaoSocial: String?, cnpjUpdated: String?, status: Situacao?, cnpj: String?, contextView: View): Boolean {
        return repositoryFornecedor.alterFornecedor(loginUseCase.getUser().username, cnpjUpdated, razaoSocial, status, cnpj, loginUseCase.getUser().name, contextView)
    }
}