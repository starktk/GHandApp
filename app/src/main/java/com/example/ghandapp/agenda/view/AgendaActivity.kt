package com.example.ghandapp.agenda.view

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.agenda.presentation.AgendaViewModel
import com.example.ghandapp.agenda.presentation.model.AgendaViewState
import com.example.ghandapp.databinding.ActivityAgendaBinding
import java.time.format.DateTimeFormatter
import java.util.Date

class AgendaActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAgendaBinding

    private lateinit var dataPartida: EditText

    private val viewModel: AgendaViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.agendarData.setOnClickListener {
            viewModel.validateInputs(
                nomeProduto = binding.nameProduct.text.toString(),
                amount = binding.amount.text.toString().toInt(),
                date = binding.dateTimePicker.toString()
            )
        }

        initializerObserve()
    }

    private fun initializerObserve() {
        viewModel.state.observe(this) { viewState ->
                when (viewState) {
                    AgendaViewState.genericError -> showGenericError()
                    AgendaViewState.dayToMarkError -> showDateError()
                    AgendaViewState.amountError -> showAmountError()
                    AgendaViewState.showLoading -> showLoading()
                    AgendaViewState.razaoSocialErrorMessage -> showRazaoSocialErrorMessage()
                    AgendaViewState.nameErrorMessage -> showNameError()
                }
            }
        }


    private fun showNameError() {
        TODO("Not yet implemented")
    }

    private fun showAmountError() {
        TODO("Not yet implemented")
    }


    private fun showRazaoSocialErrorMessage() {
        TODO("Not yet implemented")
    }

    private fun showDateError() {

    }

    private fun showLoading() {

    }

    private fun showGenericError() {

    }
}