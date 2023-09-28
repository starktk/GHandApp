package com.example.ghandapp.agenda.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ghandapp.agenda.presentation.model.AgendaViewState
import java.time.format.DateTimeFormatter

class AgendaViewModel: ViewModel() {

    private val viewState = MutableLiveData<AgendaViewState>()
    val state: LiveData<AgendaViewState> = viewState

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    fun validateInputs(nomeProduto: String, amount: int, date: String) {
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

        fetchDate()
    }
}