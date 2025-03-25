package com.example.ghandapp.home.presentation.model

import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.usuario.login.data.local.UserEntity

sealed class HomeViewState {

    data class showHomeScreen(val list: List<FornecedorModel>): HomeViewState()
    data class showFornecedorSingle(val fornecedor: FornecedorModel): HomeViewState()
    data class showAgendaProdutoScreen(val list: List<AgendaProdutoModel>): HomeViewState()
    data class showAgendaPagamentoScreen(val list: List<AgendaPagamentoModel>): HomeViewState()

    data class showProfile(val user: UserEntity): HomeViewState()
    data class sucessUser(val name: String): HomeViewState()

    data class showSucessUserEdited(val user: UserEntity): HomeViewState()

    object showFailedMessageUpdateUser: HomeViewState()
    object stateFornecedor: HomeViewState()
    object stateAgenda: HomeViewState()
    object stateAgendaPayment: HomeViewState()
    object showLoading: HomeViewState()
    object changeStatus: HomeViewState()
    object showFailedMessage: HomeViewState()
    object showEmptyList: HomeViewState()
    object showEmptyAgenda: HomeViewState()
    object showFailedStatusMessage: HomeViewState()
    object showFailedUpdateMessage: HomeViewState()
    object showSucessDeletedMessage : HomeViewState()
    object showFailedMessageToDelete : HomeViewState()
    object showFailedUser : HomeViewState()

}
