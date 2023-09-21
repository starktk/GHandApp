package com.example.ghandapp.register.registerFornecedor.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghandapp.register.registerFornecedor.presentation.model.FornecedorViewState

class FornecedorViewModel: ViewModel() {

    private val viewState = MutableLiveData<FornecedorViewState>()
    val state: LiveData<FornecedorViewState> = viewState


    fun validateInputs() {

    }
}