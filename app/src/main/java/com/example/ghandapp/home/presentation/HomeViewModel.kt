package com.example.ghandapp.home.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.agendaPagamento.data.domain.AgendaPagamentoUseCase
import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.agenda.agendaProduto.data.domain.AgendaProdutoUseCase
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.example.ghandapp.home.presentation.enums.StateStart
import com.example.ghandapp.usuario.login.data.domain.LoginUseCase
import kotlinx.coroutines.launch
import java.util.regex.Pattern

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

    fun initializer(state: StateStart, contextView: View) {
        when (state) {
            StateStart.FORNECEDOR -> oberserveCacheFornecedor(contextView)
            StateStart.AGENDAPROD -> observeCacheAgendaProdutos(contextView)
            StateStart.AGENDAPAG -> observeCacheAgendaPagamento(contextView)
        }
    }

    private fun observeCacheAgendaPagamento(contextView: View) {
        viewState.value = HomeViewState.stateAgendaPayment
        listAgendaPayment(contextView)
    }

    fun getUsername() {
        viewModelScope.launch {
            val name = fornecedorUseCase.getName()
            if (name.isEmpty()) {
                viewState.value = HomeViewState.showFailedUser
            } else {
                viewState.value = HomeViewState.sucessUser(name)
            }
        }
    }
    private fun oberserveCacheFornecedor(contextView: View) {
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

    private fun observeCacheAgendaProdutos(contextView: View) {
        viewState.value = HomeViewState.stateAgenda
        listAgendaProdutos(contextView)
    }

    fun listAgendaPayment(contextView: View) {
        viewModelScope.launch {
            val list = agendaPagamentoUseCase.listAgenda(contextView)
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showAgendaPagamentoScreen(list)
            }
        }
    }

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

    fun searchFornecedores(contextView: View, conteudo: String) {
        viewState.value = HomeViewState.showLoading
        val patternLetters = Pattern.compile("[a-zA-Z]+")
        val matcherLetters = patternLetters.matcher(conteudo)
        val PatternNumbers = Pattern.compile("\\d+")
        val matcherNumbers = PatternNumbers.matcher(conteudo)
        if (matcherLetters.matches()) {
            listByRazaoSocial(conteudo, contextView)
        } else if (matcherNumbers.matches() && conteudo.isEmpty() || conteudo.length == 11) {
            println("test 1")
            findFornecedorByCnpj(conteudo)
        } else {
            viewState.value = HomeViewState.showEmptyList
        }
    }
    fun listFornecedoresRefresh(contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.stateFornecedor
            val list = fornecedorUseCase.refresh(contextView)
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
                fornecedorUseCase.getAllFornecedores(contextView)
                listFornecedoresRefresh(contextView)
            } else {
                viewState.value = HomeViewState.showFailedUpdateMessage
            }
        }
    }
    fun modifyStatusFornecedor(cnpj: String, status: String) {
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
            val list = fornecedorUseCase.filterRazaoSocialInCache(razaoSocial)
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showHomeScreen(list)
            }

        }
    }
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
    fun listAgendaProdutos(contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val agenda = agendaProdutoUseCase.listAgenda(contextView)
            if (agenda.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showAgendaProdutoScreen(agenda)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun listAgendaProdutoByMonth(mes: Long, contextView: View) {
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
    fun listAgendaProdByStatus(status: SituacaoProduto, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val agenda = agendaProdutoUseCase.listAgenda(contextView)
            val agendaByStatus = agenda.filter {
                old ->
                old.situacaoProduto == status
            }
            if (agendaByStatus.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showAgendaProdutoScreen(agendaByStatus)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun listAgendaPagamento(mes: Long, contextView: View) {
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
    fun modifyStatusAgendaPagamento(status: String, cnpj: String, dateToPayOrReceive: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            if (status.equals(SituacaoPagamento.PAGA)) {
                viewState.value = HomeViewState.showFailedStatusMessage
            }
            val response = agendaPagamentoUseCase.modifyStatus(cnpj, dateToPayOrReceive, contextView)
            if (response) {
                viewState.value = HomeViewState.changeStatus
            } else {
                viewState.value = HomeViewState.showFailedUpdateMessage
            }
        }
    }
    fun deleteAgendaPag(contextView: View, cnpj: String, dateToPayOrReceive: String) {
        viewModelScope.launch {
            val deletedAgenda = agendaPagamentoUseCase.deleteAgenda(contextView, cnpj, dateToPayOrReceive)
            if (deletedAgenda) {
                viewState.value = HomeViewState.showSucessDeletedMessage
            } else {
                viewState.value = HomeViewState.showFailedMessageToDelete
            }
        }
    }
    fun listAgendaPagByStatus(status: SituacaoPagamento, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val list = agendaPagamentoUseCase.listByStatus(status, contextView)
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showAgendaPagamentoScreen(list)
            }
        }
    }
    fun listAgendaProdByStatus(status: Situacao) {
        viewModelScope.launch {
            val list = fornecedorUseCase.filterStatusInCache(status)
            if (list.isEmpty()) {
                viewState.value = HomeViewState.showEmptyList
            } else {
                viewState.value = HomeViewState.showHomeScreen(list)
            }
        }
    }
    fun modifyStatusAgendaProduto(status: SituacaoProduto, cnpj: String, dateToPayOrReceive: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            if (status.equals(SituacaoProduto.RECEBIDO)) {
                viewState.value = HomeViewState.showFailedStatusMessage
            }
            val response = agendaProdutoUseCase.modifyStatus(cnpj, dateToPayOrReceive, contextView)
            if (response) {
                viewState.value = HomeViewState.changeStatus
            } else {
                viewState.value = HomeViewState.showFailedStatusMessage
            }
        }
    }
    fun deleteAgenda(cnpj: String, dateToPayOrReceive: String, contextView: View) {
        viewModelScope.launch {
            val agenda = agendaProdutoUseCase.deleteAgenda(cnpj, dateToPayOrReceive, contextView)
            if (agenda) {
                viewState.value = HomeViewState.showSucessDeletedMessage
            } else {
                viewState.value = HomeViewState.showFailedMessageToDelete
            }

        }
    }
    fun showProfile() {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val user = logUsecase.getUser()
            try {
                viewState.value = HomeViewState.showProfile(user)
            } catch (exception: NullPointerException) {
                viewState.value = HomeViewState.showFailedUser
            }
        }
    }

    fun editUser(name: String, username: String, password: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = HomeViewState.showLoading
            val user = logUsecase.modifyUser(name, username, password, contextView)
            if (user) {
                viewState.value = HomeViewState.showSucessUserEdited(logUsecase.getUser())
            } else {
                viewState.value = HomeViewState.showFailedMessageUpdateUser
            }
        }
    }
}