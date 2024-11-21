package com.example.ghandapp.fornecedor.presentation

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.fornecedor.presentation.model.FornecedorViewState
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase
import kotlinx.coroutines.launch

class FornecedorViewModel: ViewModel() {

    private val viewState = MutableLiveData<FornecedorViewState>()
    val state: LiveData<FornecedorViewState> = viewState

    private val usecaseFornecedor by lazy { FornecedorUseCase() }
    private val usecaseLogin by lazy { LoginUseCase() }
    fun validateInputs(razaoSocial: String, cnpj: String) {
        viewState.value = FornecedorViewState.showLoading

        if(!razaoSocial.isNullOrEmpty() && cnpj.isNullOrEmpty ()) {
            viewState.value = FornecedorViewState.blankOrEmptyInputs
            return
        }
        if(!cnpj.isDigitsOnly() && cnpj.length < 11 || cnpj.length > 11) {
            viewState.value = FornecedorViewState.cnpjErrorMessage
            return
        }

        fetchCreate(razaoSocial, cnpj)
    }

    private fun fetchCreate(razaoSocial: String, cnpj: String) {
        viewModelScope.launch {
            val username = usecaseLogin.getUser().username
            if (username.isNullOrEmpty()) {
                viewState.value = FornecedorViewState.missingUsernameReference
            }

            val isCreated = usecaseFornecedor.createFornecedor(razaoSocial, cnpj)

            if(isCreated) {
                viewState.value = FornecedorViewState.isCreated
            } else {
                viewState.value = FornecedorViewState.badCreation
            }
        }
    }
}