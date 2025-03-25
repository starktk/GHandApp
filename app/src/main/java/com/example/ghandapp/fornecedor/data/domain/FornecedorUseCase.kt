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
    suspend fun createFornecedor(razaoSocial: String, cnpj: String, contactNumber: String, eletronicAddres: String): Boolean {
        return repositoryFornecedor.createFornecedor(razaoSocial, cnpj, contactNumber, eletronicAddres, loginUseCase.getUser().username, loginUseCase.getUser().name)
    }

    suspend fun findFornecedorByCnpj(cnpj: String): FornecedorModel? {
        return repositoryFornecedor.findFornecedorByCnpj(loginUseCase.getUser().username, cnpj)
    }

    suspend fun deleteFornecedor(cnpj: String?, contextView: View): Boolean {
        return repositoryFornecedor.deleteFornecedor(loginUseCase.getUser().username, cnpj, contextView)
    }

    suspend fun modifyStatus(cnpj: String,status: Situacao): Boolean {
        return repositoryFornecedor.modifyStatus(loginUseCase.getUser().username, loginUseCase.getUser().name, cnpj, status)
    }

    suspend fun getAllFornecedores(contextView: View): List<FornecedorModel> {
        return repositoryFornecedor.getAllFornecedores(loginUseCase.getUsername(), contextView)
    }

    suspend fun refresh(contextView: View): List<FornecedorModel> {
        return repositoryFornecedor.getFornecedores(loginUseCase.getUsername(), contextView)
    }

    suspend fun findFornecedoresByRazaoSocial(razaoSocial: String, contextView: View): List<FornecedorModel> {
        return repositoryFornecedor.findByRazaoSocial(loginUseCase.getUser().name, loginUseCase.getUser().username, razaoSocial, contextView)
    }

    suspend fun findFornecedoresByStatus(status: Situacao, contextView: View): List<FornecedorModel> {
        return repositoryFornecedor.findByStatus(loginUseCase.getUser().username, status, contextView)
    }
    suspend fun getAllFornecedoresInCache(): List<FornecedorModel>{
        return repositoryFornecedor.getFornecedoresInDb(loginUseCase.getUsername())
    }

    suspend fun filterRazaoSocialInCache(razaoSocial: String): List<FornecedorModel> {
        return repositoryFornecedor.filterByRazaoSocial(razaoSocial)
    }
    suspend fun filterStatusInCache(status: Situacao): List<FornecedorModel> {
        return repositoryFornecedor.filterByStatus(status)
    }

    suspend fun alterFornecedor(razaoSocial: String?, cnpjUpdated: String?, status: Situacao?, cnpj: String?, contextView: View): Boolean {
        return repositoryFornecedor.alterFornecedor(loginUseCase.getUser().username, cnpjUpdated, razaoSocial, status, cnpj, loginUseCase.getUser().name, contextView)
    }

    suspend fun getName(): String {
        return loginUseCase.getUser().name
    }
}