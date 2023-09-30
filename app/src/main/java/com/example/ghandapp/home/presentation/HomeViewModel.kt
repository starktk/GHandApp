package com.example.ghandapp.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.data.domain.AgendaUseCase
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val viewState = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState> = viewState
    private val fornecedorUseCase by lazy {
        FornecedorUseCase()
    }
    private val logUsecase by lazy {
        LoginUseCase()
    }
    private val agendaUseCase by lazy {
        AgendaUseCase()
    }

    fun listFornecedor() {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val id = logUsecase.getUsername()
            val list = fornecedorUseCase.getAllFornecedores(id)

            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showHomeScreen(list)
            }
        }
    }

    fun listAgenda() {

    }


}