package com.example.ghandapp.fornecedor.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghandapp.fornecedor.presentation.model.FornecedorViewState

class FornecedorViewModel: ViewModel() {

    private val viewState = MutableLiveData<FornecedorViewState>()
    val state: LiveData<FornecedorViewState> = viewState


    fun validateInputs() {

    }
}