package com.example.ghandapp.fornecedor.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.databinding.ActivityFornecedorBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.fornecedor.presentation.FornecedorViewModel
import com.example.ghandapp.fornecedor.presentation.model.FornecedorViewState
import com.example.ghandapp.home.presentation.model.StateStart
import com.example.ghandapp.start.StartActivity
import com.example.ghandapp.usuario.login.view.LoginActivity
import com.google.android.material.snackbar.Snackbar

class FornecedorActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFornecedorBinding

    private val viewModel: FornecedorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFornecedorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerFornecedor.setOnClickListener {
            viewModel.validateInputs(
                razaoSocial = binding.razaoSocial.text.toString(),
                cnpj = binding.cnpj.text.toString()
            )
        }

        binding.iconHome.setOnClickListener {
            backHomePage()
        }
        initializeObserver()
    }

    private fun backHomePage() {
        startActivity(Intent(this@FornecedorActivity, StartActivity::class.java))
        finish()
    }
    private fun initializeObserver() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
                FornecedorViewState.isCreated -> showIsCreatedSucess()
                FornecedorViewState.genericErrorMessage -> showGenericErrorMessage()
                FornecedorViewState.showLoading -> showLoading()
                FornecedorViewState.badCreation -> badCreation()
                FornecedorViewState.razaoSocialErrorMessage -> showRazaoSocialMessageError()
                FornecedorViewState.cnpjErrorMessage -> showCnpjErrorMessage()
                FornecedorViewState.missingUsernameReference -> missingUsernameReference()
                FornecedorViewState.blankOrEmptyInputs -> showInputsInvalidMessage()
            }
        }
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }
    private fun showInputsInvalidMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.registerFornecedor, "Informações inválidas", Snackbar.LENGTH_SHORT).show()
    }

    private fun showCnpjErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.cnpj, R.string.cnpj_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showRazaoSocialMessageError() {
        binding.pbLoading.hide()
        Snackbar.make(binding.razaoSocial, R.string.razaoSocial_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun badCreation() {
        binding.pbLoading.hide()
        Snackbar.make(binding.registerFornecedor, R.string.invalid_creation, Snackbar.LENGTH_SHORT).show()
    }
    private fun showGenericErrorMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showIsCreatedSucess() {
        val intent = Intent(this@FornecedorActivity, HomeActivity::class.java)
        intent.putExtra("stateStart", StateStart.FORNECEDOR.toString())
        startActivity(intent)
        finish()
    }

    private fun missingUsernameReference() {
        startActivity(Intent(this@FornecedorActivity, LoginActivity::class.java))
        finish()
    }
}