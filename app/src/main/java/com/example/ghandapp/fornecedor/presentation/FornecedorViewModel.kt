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
        if(!isValidCnpj(cnpj)) {
            viewState.value = FornecedorViewState.cnpjErrorMessage
            return
        }

        fetchCreate(razaoSocial, cnpj)
    }

    private fun isValidCnpj(cnpj: String): Boolean {
        val numbers = cnpj.replace(Regex("[^\\d]"), "")

        if (numbers.length != 14) return false

        val invalids = listOf("00000000000000", "11111111111111", "22222222222222")
        if (numbers in invalids) return false

        fun calculateDigit(cnpj: String, weights: IntArray): Int {
            var sum = 0
            for (i in weights.indices) sum += (cnpj[i].toString().toInt() * weights[i])
            val remainder = sum % 11
            return if (remainder < 2) 0 else 11 - remainder
        }

        val weight1 = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
        val weight2 = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

        val digit1 = calculateDigit(numbers, weight1)
        val digit2 = calculateDigit(numbers + digit1, weight2)

        return numbers.endsWith("$digit1$digit2")
    }

    private fun fetchCreate(razaoSocial: String, cnpj: String) {
        viewModelScope.launch {
            val username = usecaseLogin.getUser().username
            println(username)
            if (username.isEmpty()) {
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