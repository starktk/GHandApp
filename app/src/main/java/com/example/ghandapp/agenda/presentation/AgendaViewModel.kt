package com.example.ghandapp.agenda.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghandapp.agenda.data.domain.AgendaUseCase
import com.example.ghandapp.agenda.presentation.model.AgendaViewState
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class AgendaViewModel: ViewModel() {

    private val viewState = MutableLiveData<AgendaViewState>()
    val state: LiveData<AgendaViewState> = viewState
    private val usecase by lazy {
        AgendaUseCase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    fun validateInputs(nomeProduto: String, amount: Int, date: String, razaoSocial: String) {
        viewState.value = AgendaViewState.showLoading

        if(nomeProduto.isNullOrBlank() && amount <= 0 && date.equals(dateFormat)) {
            viewState.value = AgendaViewState.genericError
            return
        }
        if (nomeProduto.isNullOrBlank()) {
            viewState.value = AgendaViewState.nameErrorMessage
            return
        }
        if (amount <= 0) {
            viewState.value = AgendaViewState.amountError
            return
        }
        if (date.equals(dateFormat) || date.isNullOrBlank()) {
            viewState.value = AgendaViewState.dayToMarkError
            return
        }

        fetchDate(razaoSocial, nomeProduto, amount, date)
    }

    private fun fetchDate(razaoSocial: String, nameProduct: String, amount: Int, date: String) {
        viewModelScope.launch {
            viewState.value = AgendaViewState.showLoading

            val isSucess = usecase.createAgenda(razaoSocial, nameProduct, amount, date)

            if (isSucess) {
                viewState.value = AgendaViewState.showIsSucess
            } else {
                viewState.value = AgendaViewState.badCreation
            }
        }
    }
}