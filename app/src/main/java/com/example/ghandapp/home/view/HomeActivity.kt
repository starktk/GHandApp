package com.example.ghandapp.home.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.data.remote.AgendaModel
import com.example.ghandapp.agenda.view.AgendaActivity
import com.example.ghandapp.agenda.view.AgendaListAdapter
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.example.ghandapp.fornecedor.view.FornecedorListAdapter
import com.example.ghandapp.home.presentation.HomeViewModel
import com.example.ghandapp.home.presentation.model.HomeViewState

class HomeActivity: AppCompatActivity() {

    private lateinit var bindingActivity: ActivityHomeBinding

    private val fornecedorAdapter by lazy {
        FornecedorListAdapter()
    }

    private val agendaAdapter by lazy {
        AgendaListAdapter()
    }

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(bindingActivity.root)



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
        bindingActivity.listarAgenda.setOnClickListener {
            showCustomDialog()
        }
    }
    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                is HomeViewState.showAgendaScreen -> showAgendaList(viewState.list)
                is HomeViewState.showFornecedorSingle -> showFornecedor(viewState.fornecedor)
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
                HomeViewState.showEmptyAgenda -> showEmptyAgenda()
            }
        }
    }

    private fun showFornecedor(fornecedorModel: FornecedorModel) {
        bindingActivity.pbLoading.hide()
        fornecedorAdapter.addSingleItem(fornecedorModel)
    }

    private fun showEmptyAgenda() {
        bindingActivity.pbLoading.hide()
    }

    private fun showAgendaList(list: List<AgendaModel>) {
        bindingActivity.pbLoading.hide()
        agendaAdapter.add(list)
    }


    private fun showFornecedorList(list: List<FornecedorModel>) {
        bindingActivity.pbLoading.hide()
        fornecedorAdapter.addAllItems(list)
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
        bindingActivity.rvListagem
    }

    private fun showEmptyList() {
        bindingActivity.pbLoading.hide()
    }

    private fun listar() {
        bindingActivity.rvListagem.adapter = fornecedorAdapter
        viewModel.listFornecedor()
    }

    private fun listarAgenda(razaoSocial: String, mes: String) {
        bindingActivity.rvListagem.adapter = agendaAdapter
        viewModel.listAgenda(razaoSocial, mes)
    }

    private fun showCustomDialog() {
        val dialog = DialogCustomFragment()
        dialog.show(supportFragmentManager, dialog.tag)
        val text = R.id.btn_razaoSocial.toString()
        val button = findViewById<Button>(R.id.btn_submit)
        val spinner = findViewById<Spinner>(R.id.spinner_month)

        button.setOnClickListener {
            listarAgenda(
                razaoSocial = text,
                mes = spinner.selectedItem.toString()
            )
        }
    }
}

