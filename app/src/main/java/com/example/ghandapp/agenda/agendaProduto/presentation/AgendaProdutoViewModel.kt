package com.example.ghandapp.agenda.agendaProduto.presentation

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.agendaProduto.data.domain.AgendaProdutoUseCase
import com.example.ghandapp.agenda.agendaProduto.presentation.model.AgendaProdutoViewState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AgendaProdutoViewModel: ViewModel() {

    private val viewState = MutableLiveData<AgendaProdutoViewState>()
    val state: LiveData<AgendaProdutoViewState> = viewState
    private val usecase by lazy {
        AgendaProdutoUseCase()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun validateInputs(nomeProduto: String, amount: Int, date: String, cnpj: String, contextView: View) {
        viewState.value = AgendaProdutoViewState.showLoading
        val formato = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateNew = LocalDate.parse(date, formato)

        if(nomeProduto.isNullOrBlank() && amount <= 0 && date.isNullOrBlank() && cnpj.isNullOrBlank()) {
            viewState.value = AgendaProdutoViewState.genericError
            return
        }
        if (nomeProduto.isNullOrBlank()) {
            viewState.value = AgendaProdutoViewState.nameErrorMessage
            return
        }
        if (amount <= 0) {
            viewState.value = AgendaProdutoViewState.amountError
            return
        }
        if (dateNew.isBefore(LocalDate.now()) || date.isNullOrBlank()) {
            viewState.value = AgendaProdutoViewState.dayToMarkError
            return
        }
        if (!isValidCnpj(cnpj)) {
            viewState.value = AgendaProdutoViewState.cnpjErrorMessage
            return
        }

        fetchDate(cnpj, nomeProduto, amount, date, contextView)
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

    private fun fetchDate(cnpj: String, nameProduct: String, amount: Int, date: String, contextView: View) {
        viewModelScope.launch {
            viewState.value = AgendaProdutoViewState.showLoading

            val isSucess = usecase.createAgenda(cnpj, nameProduct, amount, date, contextView)

            if (isSucess) {
                viewState.value = AgendaProdutoViewState.showIsSucess
            } else {
                viewState.value = AgendaProdutoViewState.badCreation
            }
        }
    }
}