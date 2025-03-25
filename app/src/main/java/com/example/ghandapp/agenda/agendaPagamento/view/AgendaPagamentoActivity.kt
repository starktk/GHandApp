package com.example.ghandapp.agenda.agendaPagamento.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaPagamento.presentation.AgendaPagamentoViewModel
import com.example.ghandapp.agenda.agendaPagamento.presentation.model.AgendaPagamentoViewState
import com.example.ghandapp.databinding.ActivityAgendapagamentoBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.presentation.enums.StateStart
import com.example.ghandapp.home.view.HomeActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgendaPagamentoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAgendapagamentoBinding
    private val viewModel: AgendaPagamentoViewModel by viewModels()
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendapagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dateAgendaPag.setOnClickListener {
            openDialog {
                date -> selectedDate = date
            }
        }

        binding.registerAgendaPagamento.setOnClickListener {
            viewModel.validateInputs(
                valueToPay = binding.valueToPay.text.toString(),
                dateToPayOrReceive = selectedDate,
                cnpj = binding.cnpjDigite.text.toString(),
                binding.root
            )
        }

        binding.iconHome.setOnClickListener {
            backHomePage()
        }

        initializerObserve()
    }

    private fun openDialog(onDateSelected: (String) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecione a data")
            .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener { selection ->
            val formattedDate = convertTimestampToDate(selection)
            onDateSelected(formattedDate)
        }
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return outputFormat.format(date)
    }

    private fun backHomePage() {
        val intent = Intent(this@AgendaPagamentoActivity, HomeActivity::class.java)
        intent.putExtra("stateStart", StateStart.AGENDAPAG as Parcelable)
        startActivity(intent)
        finish()
    }

    private fun initializerObserve() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
                AgendaPagamentoViewState.badCreation -> showBadCreation()
                AgendaPagamentoViewState.cnpjErrorMessage -> showCnpjErrorMessage()
                AgendaPagamentoViewState.genericErrorMessage -> showGenericErrorMessage()
                AgendaPagamentoViewState.showLoading -> showLoading()
                AgendaPagamentoViewState.showSucess -> showSucess()
                AgendaPagamentoViewState.valueErrorMessage -> showValueErrorMessage()
                AgendaPagamentoViewState.dateErrorMessage -> showDateErrorMessage()
            }
        }
    }

    private fun showDateErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Data inválida", Snackbar.LENGTH_SHORT).show()
    }

    private fun showValueErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.errorValue, Snackbar.LENGTH_SHORT).show()
    }

    private fun showSucess() {
        val intent = Intent(this@AgendaPagamentoActivity, HomeActivity::class.java)
        intent.putExtra("stateStart", StateStart.AGENDAPAG as Parcelable)
        startActivity(intent)
        finish()
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showGenericErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showCnpjErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.cnpj_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showBadCreation() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.badCration, Snackbar.LENGTH_SHORT).show()
    }

}
