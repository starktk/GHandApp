package com.example.ghandapp.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.data.domain.AgendaUseCase
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.SituacaoFornecedor
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
            val list = fornecedorUseCase.getAllFornecedores()
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showHomeScreen(list)
            }
        }
    }
    fun modifyStatus(cnpj: String, status: String) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val statusOf = SituacaoFornecedor.valueOf(status)
            val response = fornecedorUseCase.modifyStatus(cnpj, statusOf)

            if (response) {
                viewState.value = HomeViewState.changeStatus
            } else {
                viewState.value = HomeViewState.showFailedMessage
            }
        }
    }
    fun findFornecedorByCnpj(cnpj: String) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val modelResponse = fornecedorUseCase.findFornecedorByCnpj(cnpj)

            if (modelResponse == null) {
                viewState.value = HomeViewState.showFailedMessage
            } else {
                viewState.value = HomeViewState.showFornecedorSingle(modelResponse)
            }
        }
    }

    fun listAgenda(razaoString: String, mes: String) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val agenda = agendaUseCase.findAgendaByMonth(razaoString, mes)

            if (agenda.isNullOrEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            }
        }
    }


}