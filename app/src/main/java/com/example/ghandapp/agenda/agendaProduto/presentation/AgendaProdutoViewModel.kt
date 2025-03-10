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
        if (cnpj.isNullOrBlank()) {
            viewState.value = AgendaProdutoViewState.cnpjErrorMessage
            return
        }

        fetchDate(cnpj, nomeProduto, amount, date, contextView)
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