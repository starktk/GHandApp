package com.example.ghandapp.fornecedor.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.databinding.ActivityFornecedorBinding
import com.example.ghandapp.home.view.HomeActivity
import com.example.ghandapp.fornecedor.presentation.FornecedorViewModel
import com.example.ghandapp.fornecedor.presentation.model.FornecedorViewState

class FornecedorActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFornecedorBinding

    private val viewModel: FornecedorViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFornecedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeObserver()
    }

    private fun initializeObserver() {
        viewModel.state.observe(this) { viewState ->
            when (viewState) {
            FornecedorViewState.isCreated -> showIsCreatedSucess()
            FornecedorViewState.genericErrorMessage -> showGenericErrorMessage()
            }
        }
    }

    private fun showGenericErrorMessage() {
        TODO("Not yet implemented")
    }

    private fun showIsCreatedSucess() {
        startActivity(Intent(this@FornecedorActivity, HomeActivity::class.java))
        finish()
    }

}