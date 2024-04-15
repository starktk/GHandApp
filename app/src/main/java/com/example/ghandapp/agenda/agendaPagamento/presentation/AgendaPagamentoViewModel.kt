package com.example.ghandapp.agenda.agendaPagamento.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.agendaPagamento.data.domain.AgendaPagamentoUseCase
import com.example.ghandapp.agenda.agendaPagamento.presentation.model.AgendaPagamentoViewState
import kotlinx.coroutines.launch

class AgendaPagamentoViewModel: ViewModel() {

    private val viewState = MutableLiveData<AgendaPagamentoViewState>()
    val state: LiveData<AgendaPagamentoViewState> = viewState

    private val useCase by lazy {
        AgendaPagamentoUseCase()
    }

    fun validateInputs(valueToPay: String, dateToPayOrReceive: String ,cnpj: String, contextView: View) {
        viewState.value = AgendaPagamentoViewState.showLoading
        if (valueToPay.isNullOrEmpty() && cnpj.isNullOrEmpty()) {
            viewState.value = AgendaPagamentoViewState.genericErrorMessage
            return
        }
        if (valueToPay.isNullOrEmpty() || valueToPay.equals("0")) {
            viewState.value = AgendaPagamentoViewState.valueErrorMessage
            return
        }
        if (cnpj.isNullOrEmpty() || cnpj.length >= 4) {
            viewState.value = AgendaPagamentoViewState.cnpjErrorMessage
            return
        }
        if (dateToPayOrReceive.length == 0 || dateToPayOrReceive.isNullOrEmpty()) {
            viewState.value = AgendaPagamentoViewState.dateErrorMessage
            return
        }
        fetchCreation(valueToPay.toDouble(), cnpj, dateToPayOrReceive, contextView)
    }

    private fun fetchCreation(valueToPay: Double, cnpj: String,dateToPayOrReceive: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = AgendaPagamentoViewState.showLoading

            val response = useCase.createDate(cnpj, valueToPay, dateToPayOrReceive, contextView)

            if (!response) {
                viewState.value = AgendaPagamentoViewState.badCreation
            } else {
                viewState.value = AgendaPagamentoViewState.showSucess
            }
        }
    }
}