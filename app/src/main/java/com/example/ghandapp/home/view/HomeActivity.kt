package com.example.ghandapp.home.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.fornecedor.data.remote.FornecedorModel
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.example.ghandapp.fornecedor.view.FornecedorListAdapter
import com.example.ghandapp.home.presentation.HomeViewModel
import com.example.ghandapp.home.presentation.model.HomeViewState

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

        initializeOberseve()

    }

    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                HomeViewState.showAgenda -> showAgenda()
                HomeViewState.showFindAgenda -> findAgenda()
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
            }
        }
    }


    private fun showFornecedorList(list: List<FornecedorModel>) {
        bindingActivity.pbLoading.hide()
        fornecedorAdapter.add(list)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.fornecedor -> {
                true
            }
            R.id.list -> {
                listar()
                true
            }
            R.id.registro -> {
                showRegisterScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
            }
        }

        private fun showRegisterScreen() {
            startActivity(Intent(this@HomeActivity, FornecedorActivity::class.java))
            finish()
        }

    private fun showLoading() {
        bindingActivity.pbLoading.show()
    }

    private fun showAgenda() {

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

