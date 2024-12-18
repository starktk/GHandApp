package com.example.ghandapp.home.presentation

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.agendaPagamento.data.domain.AgendaPagamentoUseCase
import com.example.ghandapp.agenda.agendaProduto.data.domain.AgendaProdutoUseCase
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.example.ghandapp.home.presentation.enums.StateStart
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
    private val agendaProdutoUseCase by lazy {
        AgendaProdutoUseCase()
    }
    private val agendaPagamentoUseCase by lazy {
        AgendaPagamentoUseCase()
    }

    fun initializer(state: String, contextView: View) {
        when (state) {
            StateStart.FORNECEDOR.toString() -> oberserveCache(contextView)
            StateStart.AGENDA.toString() -> viewState.value = HomeViewState.stateAgenda
        }
    }
    private fun oberserveCache(contextView: View) {
        viewState.value = HomeViewState.stateFornecedor
        viewModelScope.launch{
            val verifyEqualsList = areListsContentDifferent(fornecedorUseCase.getAllFornecedoresInCache(), fornecedorUseCase.getAllFornecedores(contextView))
            if (verifyEqualsList) {
                listFornecedor(fornecedorUseCase.getAllFornecedores(contextView))
            } else {
                listFornecedor(fornecedorUseCase.getAllFornecedoresInCache())
            }
        }
    }
    private fun <T> areListsContentDifferent(list1: List<T>, list2: List<T>): Boolean {
        return list1.toSet() != list2.toSet() }

    @SuppressLint("SuspiciousIndentation")
    private fun listFornecedor(list: List<FornecedorModel>) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
                if (list.isEmpty()) {
                    viewState.value = HomeViewState.showEmptyList
                } else {
                    viewState.value = HomeViewState.showHomeScreen(list)
                }
        }
    }
    fun listFornecedoresRefresh(contextView: View) {
        viewModelScope.launch {
            val list = fornecedorUseCase.getAllFornecedores(contextView)
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showHomeScreen(list)
            }
        }
    }
    fun updateFornecedor(fornecedorModel: FornecedorModel, cnpj: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val fornecedorUpdated = fornecedorUseCase.alterFornecedor(fornecedorModel.razaoSocial, fornecedorModel.cnpj, fornecedorModel.status, cnpj, contextView)
            if (fornecedorUpdated) {
                val list = fornecedorUseCase.getAllFornecedores(contextView)
                listFornecedor(list)
            } else {
                viewState.value = HomeViewState.showFailedUpdateMessage
            }
        }
    }
    fun modifyStatus(cnpj: String, status: String) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val statusOf = Situacao.valueOf(status)
            val response = fornecedorUseCase.modifyStatus(cnpj, statusOf)
            if (response) {
                viewState.value = HomeViewState.changeStatus
            } else {
                viewState.value = HomeViewState.showFailedStatusMessage
            }
        }
    }
    fun deleteFornecedor(cnpj: String?, contextView: View) {
        println(cnpj)
        viewModelScope.launch {
            val response = fornecedorUseCase.deleteFornecedor(cnpj, contextView)
            println(response)
            if (response) {
                viewState.value = HomeViewState.showSucessDeletedMessage
            } else {
                viewState.value = HomeViewState.showFailedMessageToDelete
            }
        }
    }
    fun listByRazaoSocial(razaoSocial: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val list = fornecedorUseCase.findFornecedoresByRazaoSocial(razaoSocial, contextView)
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showHomeScreen(list)
            }

        }
    }

//    fun listByStatus(status: Situacao) {
//        viewModelScope.launch {
//            viewState.value = HomeViewState.showLoading
//            val listFornecedores = fornecedorUseCase.findFornecedoresByStatus(status)
//
//        }
//    }

    fun findFornecedorByCnpj(cnpj: String) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            if (cnpj.isNullOrEmpty()) {
                viewState.value = HomeViewState.showFailedMessage
            }
            val modelResponse = fornecedorUseCase.findFornecedorByCnpj(cnpj)

            if (modelResponse == null) {
                viewState.value = HomeViewState.showFailedMessage
            } else {
                viewState.value = HomeViewState.showFornecedorSingle(modelResponse)
            }
        }
    }

//    fun getNameToShow() {
//        viewModelScope.launch {
//            logUsecase.getUser().name
//        }
//    }
    fun listAgendaProduto(mes: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val agenda = agendaProdutoUseCase.findAgendaByMonth(mes, contextView)

            if (agenda.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showAgendaProdutoScreen(agenda)
            }
        }
    }
    fun listAgendaPagamento(mes: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val agenda = agendaPagamentoUseCase.findAgendaByMonth(mes, contextView)

            if (agenda.isEmpty()) {
                viewState.value = HomeViewState.showAgendaPagamentoScreen(agenda)
            } else {
                viewState.value = HomeViewState.showEmptyList
            }
        }
    }

}