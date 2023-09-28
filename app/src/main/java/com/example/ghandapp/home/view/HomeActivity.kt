package com.example.ghandapp.home.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.view.AgendaActivity
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.fornecedor.data.remote.FornecedorModel
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.example.ghandapp.fornecedor.view.FornecedorListAdapter
import com.example.ghandapp.home.presentation.HomeViewModel
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.google.android.material.appbar.MaterialToolbar

class HomeActivity: AppCompatActivity() {

    private lateinit var bindingActivity: ActivityHomeBinding

    private val fornecedorAdapter by lazy {
        FornecedorListAdapter()
    }

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(bindingActivity.root)

        bindingActivity.rvListagem.adapter = fornecedorAdapter

        observeEvents()
        initializeOberseve()

    }

    private fun observeEvents() {
        bindingActivity.iconRegisterFornecedor.setOnClickListener {
            showRegisterScreen()
        }
        bindingActivity.iconListFornecedor.setOnClickListener {
            listar()
        }
        bindingActivity.iconAgendarProducts.setOnClickListener {
            showAgenda()
        }
    }
    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
            }
        }
    }


    private fun showFornecedorList(list: List<FornecedorModel>) {
        bindingActivity.pbLoading.hide()
        fornecedorAdapter.add(list)
    }

    private fun showRegisterScreen() {
        startActivity(Intent(this@HomeActivity, FornecedorActivity::class.java))
        finish()
    }

    private fun showLoading() {
        bindingActivity.pbLoading.show()
    }

    private fun showAgenda() {
        startActivity(Intent(this@HomeActivity, AgendaActivity::class.java))
        finish()
    }

    private fun findAgenda() {

    }

    private fun showEmptyList() {
        bindingActivity.pbLoading.hide()
    }

    private fun listar() {
        viewModel.listFornecedor()
    }
}

