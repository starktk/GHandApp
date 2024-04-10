package com.example.ghandapp.agenda.agendaPagamento.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaPagamento.presentation.AgendaPagamentoViewModel
import com.example.ghandapp.agenda.agendaPagamento.presentation.model.AgendaPagamentoViewState
import com.example.ghandapp.databinding.ActivityAgendapagamentoBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.presentation.model.StateStart
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.start.StartActivity
import com.google.android.material.snackbar.Snackbar

class AgendaPagamentoActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAgendapagamentoBinding

    private val viewModel: AgendaPagamentoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendapagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.agendarData.setOnClickListener {
            viewModel.validateInputs(
                valueToPay = binding.valueToPay.text.toString(),
                cnpj = binding.cnpjDigite.text.toString(),
                binding.root
            )
        }

        binding.iconHome.setOnClickListener {
            backHomePage()
        }

        initializerObserve()
    }

    private fun backHomePage() {
        startActivity(Intent(this@AgendaPagamentoActivity, StartActivity::class.java))
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
            }
        }
    }

    private fun showValueErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.errorValue, Snackbar.LENGTH_SHORT).show()
    }

    private fun showSucess() {
        val intent = Intent(this@AgendaPagamentoActivity, HomeActivity::class.java)
        intent.putExtra("stateStart", StateStart.AGENDA.toString())
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