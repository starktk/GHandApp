package com.example.ghandapp.agenda.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.presentation.AgendaViewModel
import com.example.ghandapp.agenda.presentation.model.AgendaViewState
import com.example.ghandapp.databinding.ActivityAgendaBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.view.HomeActivity
import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter
import java.util.Date

class AgendaActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAgendaBinding

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
                date = binding.dateTimePicker.toString(),
                razaoSocial = binding.razaoSocial.text.toString()
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
                    AgendaViewState.badCreation -> showBadCreation()
                    AgendaViewState.showIsSucess -> showIsSucess()
                }
            }
        }

    private fun showBadCreation() {
        binding.pbLoading.hide()
        Snackbar.make(binding.agendarData, "Agendamento Inválido", Snackbar.LENGTH_SHORT).show()
    }

    private fun showIsSucess() {
        binding.pbLoading.hide()
        startActivity(Intent(this@AgendaActivity, HomeActivity::class.java))
        finish()
    }


    private fun showNameError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.nameProduct, getString(R.string.nameProduct_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun showAmountError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.amount, getString(R.string.amount), Snackbar.LENGTH_SHORT).show()
    }


    private fun showRazaoSocialErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.razaoSocial, getString(R.string.razaoSocial_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun showDateError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.dateTimePicker, getString(R.string.date_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showGenericError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, getString(R.string.generic_error), Snackbar.LENGTH_SHORT).show()
    }


}