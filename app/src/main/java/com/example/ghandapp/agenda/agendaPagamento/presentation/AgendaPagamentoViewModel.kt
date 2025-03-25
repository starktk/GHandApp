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
        if (!isValidCnpj(cnpj)) {
            viewState.value = AgendaPagamentoViewState.cnpjErrorMessage
            return
        }
        if (dateToPayOrReceive.length == 0 || dateToPayOrReceive.isNullOrEmpty()) {
            viewState.value = AgendaPagamentoViewState.dateErrorMessage
            return
        }
        fetchCreation(valueToPay.toDouble(), cnpj, dateToPayOrReceive, contextView)
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