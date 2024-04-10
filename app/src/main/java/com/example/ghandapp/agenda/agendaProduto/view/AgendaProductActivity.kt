package com.example.ghandapp.agenda.agendaProduto.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaProduto.presentation.AgendaProdutoViewModel
import com.example.ghandapp.agenda.agendaProduto.presentation.model.AgendaProdutoViewState
import com.example.ghandapp.databinding.ActivityAgendaproductBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.presentation.model.StateStart
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.start.StartActivity
import com.google.android.material.snackbar.Snackbar

class AgendaProductActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAgendaproductBinding

    private val viewModel: AgendaProdutoViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaproductBinding.inflate(layoutInflater)

        setContentView(binding.root)



        binding.agendarData.setOnClickListener {
            viewModel.validateInputs(
                nomeProduto = binding.nameProduct.text.toString(),
                amount = binding.amount.text.toString().toInt(),
                date = binding.dateTimePicker.toString(),
                cnpj = binding.digiteCnpj.toString(),
                binding.root
            )
        }

        binding.iconHome.setOnClickListener {
            backHomePage()
        }

        initializerObserve()
    }

    private fun backHomePage() {
        startActivity(Intent(this@AgendaProductActivity, StartActivity::class.java))
        finish()
    }

    private fun initializerObserve() {
        viewModel.state.observe(this) { viewState ->
                when (viewState) {
                    AgendaProdutoViewState.genericError -> showGenericError()
                    AgendaProdutoViewState.dayToMarkError -> showDateError()
                    AgendaProdutoViewState.amountError -> showAmountError()
                    AgendaProdutoViewState.showLoading -> showLoading()
                    AgendaProdutoViewState.nameErrorMessage -> showNameError()
                    AgendaProdutoViewState.badCreation -> showBadCreation()
                    AgendaProdutoViewState.showIsSucess -> showIsSucess()
                    AgendaProdutoViewState.cnpjErrorMessage -> showCnpjMessage()
                }
            }
        }

    private fun showCnpjMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.cnpj_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showBadCreation() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Agendamento Inválido", Snackbar.LENGTH_SHORT).show()
    }

    private fun showIsSucess() {
        val intent = Intent(this@AgendaProductActivity, HomeActivity::class.java)
        intent.putExtra("stateStart", StateStart.AGENDA.toString())
        startActivity(intent)
        finish()
    }


    private fun showNameError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, getString(R.string.nameProduct_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun showAmountError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, getString(R.string.amount), Snackbar.LENGTH_SHORT).show()
    }




    private fun showDateError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, getString(R.string.date_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showGenericError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, getString(R.string.generic_error), Snackbar.LENGTH_SHORT).show()
    }


}