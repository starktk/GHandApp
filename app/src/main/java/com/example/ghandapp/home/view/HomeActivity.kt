package com.example.ghandapp.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaPagamento.view.AgendaPagamentoActivity
import com.example.ghandapp.agenda.agendaPagamento.view.AgendaPagamentoListAdapter
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.view.AgendaProductActivity
import com.example.ghandapp.agenda.agendaProduto.view.AgendaProdutoListAdapter
import com.example.ghandapp.databinding.ActivityHomeBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import com.example.ghandapp.fornecedor.view.FornecedorActivity
import com.example.ghandapp.fornecedor.view.FornecedorListAdapter
import com.example.ghandapp.home.presentation.HomeViewModel
import com.example.ghandapp.home.presentation.enums.StateStart
import com.example.ghandapp.home.presentation.model.HomeViewState
import com.example.ghandapp.usuario.login.view.LoginActivity
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate


class HomeActivity: AppCompatActivity(), AgendaDialogFragment.AgendaValue {
    var contextScreen: StateStart = StateStart.FORNECEDOR
    private lateinit var binding: ActivityHomeBinding
    private var activeMenu: PopupMenu? = null
    private var contextMonthFilter: Boolean = false

    private val fornecedorAdapter by lazy {
        FornecedorListAdapter(
            onStatusChange = { fornecedor ->
                viewModel.modifyStatusFornecedor(
                    fornecedor.cnpj.toString(),
                    fornecedor.status.toString().uppercase()
            )
        },
            onEditChange = { fornecedor, cnpj ->
                if (cnpj != null) {
                    viewModel.updateFornecedor(fornecedor, cnpj, binding.root)
                    viewModel.listFornecedoresRefresh(binding.root)
                }
            }
        )

    }

    private val agendaProdutoAdapter by lazy {
        AgendaProdutoListAdapter(
            onStatusChange = {
                agendaProduto -> viewModel.modifyStatusAgendaProduto(agendaProduto.situacaoProduto, agendaProduto.cnpj, agendaProduto.date, binding.root)
                viewModel.listAgendaProdutos(binding.root)

            }
        )
    }
    private val agendaPagamentoAdapter by lazy {
        AgendaPagamentoListAdapter()
    }

    private val viewModel: HomeViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refresh()
        observeEvents()
        init()
        initializeOberseve()
        setupItemTouchHelper(binding.rvList)
    }
    private fun init() {
        viewModel.getUsername()
        binding.fornecedorScreen.setOnClickListener {
            contextScreen = StateStart.FORNECEDOR
            viewModel.initializer(StateStart.FORNECEDOR, binding.root)
        }
        binding.agendaScreen.setOnClickListener {
            contextScreen = StateStart.AGENDA
            viewModel.initializer(StateStart.AGENDA, binding.root)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeOberseve() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is HomeViewState.showHomeScreen -> showFornecedorList(viewState.list)
                is HomeViewState.showAgendaProdutoScreen -> showAgendaProdutoList(viewState.list)
                is HomeViewState.showFornecedorSingle -> showFornecedor(viewState.fornecedor)
                is HomeViewState.showAgendaPagamentoScreen -> showAgendaPagamentoList(viewState.list)
                is HomeViewState.sucessUser -> showNameForFinalUser(viewState.name)
                HomeViewState.showFailedUser -> userFail()
                HomeViewState.showEmptyList -> showEmptyList()
                HomeViewState.showLoading -> showLoading()
                HomeViewState.showEmptyAgenda -> showEmptyAgenda()
                HomeViewState.stateFornecedor -> bindForFornecedor()
                HomeViewState.stateAgenda -> bindAgenda()
                HomeViewState.changeStatus -> showMessageStatus()
                HomeViewState.showFailedMessage -> showFailMessage()
                HomeViewState.showFailedStatusMessage -> showFailedStatusMessage()
                HomeViewState.showFailedUpdateMessage -> showFailedUpdateMessage()
                HomeViewState.showFailedMessageToDelete -> showFailedMessageToDelete()
                HomeViewState.showSucessDeletedMessage -> showSucessDeletedMessage()
            }
        }
    }

    private fun userFail() {
        Snackbar.make(binding.root, "Faça Login novamente", Snackbar.LENGTH_SHORT).show()
    }

    private fun showNameForFinalUser(name: String) {
        binding.userName.text = name
        viewModel.initializer(StateStart.FORNECEDOR, binding.root)
    }

    private fun refresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (contextScreen == StateStart.FORNECEDOR) {
                viewModel.listFornecedoresRefresh(binding.root)
                binding.swipeRefreshLayout.postDelayed({
                    binding.swipeRefreshLayout.isRefreshing = false
                }, 2000)
            } else {
                binding.monthFilter.setSelection(0)
                viewModel.listAgendaProdutos(binding.root)
                binding.swipeRefreshLayout.postDelayed({
                    binding.swipeRefreshLayout.isRefreshing = false
                }, 2000)
            }

        }
    }
    private fun showFailedMessageToDelete() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi possivel deletar", Snackbar.LENGTH_LONG).show()
    }

    private fun showSucessDeletedMessage() {
        binding.pbLoading.hide()
        val emoji = "✅"
        Snackbar.make(binding.root, emoji, Snackbar.LENGTH_LONG).show()
    }

    private fun setupItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder ):
                        Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    if (contextScreen == StateStart.FORNECEDOR) {
                        viewModel.deleteFornecedor(fornecedorAdapter.getObjectInListByPosition(position).cnpj, binding.root)
                        fornecedorAdapter.removeItem(position)
                    } else {
                        viewModel.deleteAgenda(agendaProdutoAdapter.getObjectInListByPosition(position).cnpj, agendaProdutoAdapter.getObjectInListByPosition(position).date, binding.root)
                        agendaProdutoAdapter.removeItem(position)
                    }
                }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    private fun showFailedUpdateMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Edição inválida", Snackbar.LENGTH_LONG).show()
    }

    private fun showFailedStatusMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi possivel modificar o status", Snackbar.LENGTH_LONG).show()
    }

    private fun showMessageStatus() {
//        binding.pbLoading.hide()
//        Snackbar.make(binding.root, "Status modificado com sucesso", Snackbar.LENGTH_LONG).show()
    }


    private fun observeEvents() {
        binding.iconProfile.setOnClickListener{
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
    }

    private fun showAgendaPagamentoList(list: List<AgendaPagamentoModel>) {
        binding.pbLoading.hide()
        agendaPagamentoAdapter.add(list)
    }

    private fun showFailMessage() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Procura inválida", Snackbar.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindAgenda() {
        binding.rvList.adapter = agendaProdutoAdapter
        binding.searchMenu.visibility = View.GONE
        binding.searchMenuAgenda.visibility = View.VISIBLE
        val swipeParams = binding.swipeRefreshLayout.layoutParams as ConstraintLayout.LayoutParams
        swipeParams.topToBottom = binding.searchMenuAgenda.id
        binding.swipeRefreshLayout.layoutParams = swipeParams
        binding.iconAddsScreenContext.setOnClickListener {
            if (contextScreen.equals(StateStart.AGENDA)) {
                startActivity(Intent(this@HomeActivity, AgendaProductActivity::class.java))
            }
        }
        binding.monthFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val itemSelecionado = parent.getItemAtPosition(position).toString()

                if (itemSelecionado != "Selecione uma opção") {
                    listarAgendaProduto(filterMonth().toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        binding.iconProfile.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
    }

    @SuppressLint("ResourceType")
    private fun bindForFornecedor() {
        binding.rvList.adapter = fornecedorAdapter
        binding.searchMenuAgenda.visibility = View.GONE
        binding.searchMenu.visibility = View.VISIBLE
        val swipeParams = binding.swipeRefreshLayout.layoutParams as ConstraintLayout.LayoutParams
        swipeParams.topToBottom = binding.searchMenu.id
        binding.swipeRefreshLayout.layoutParams = swipeParams
        binding.swStatus.isChecked = false
        binding.iconAdd.setOnClickListener {
            showRegisterFornecedorScreen()
        }
        binding.iconProfile.setOnClickListener {
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
        binding.iconSearch.setOnClickListener {
            val textToSearch = binding.searchBar.text.toString()
            viewModel.searchFornecedores(binding.root, textToSearch)
        }
        binding.swStatus.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck){
                    viewModel.listByStatus(Situacao.INATIVA)
                } else {
                    viewModel.listByStatus(Situacao.ATIVA)
                }
            }

    }


    private fun showRegisterAgendaProdutoScren() {
        startActivity(Intent(this@HomeActivity, AgendaProductActivity::class.java))
    }
    private fun showRegisterAgendaPagamentoScreen() {
        startActivity(Intent(this@HomeActivity, AgendaPagamentoActivity::class.java))
    }

    private fun showFornecedor(fornecedorModel: FornecedorModel) {
        binding.pbLoading.hide()
        fornecedorAdapter.addSingleItem(fornecedorModel)
    }

    private fun showEmptyAgenda() {
        binding.pbLoading.hide()
    }

    private fun showAgendaProdutoList(list: List<AgendaProdutoModel>) {
        binding.pbLoading.hide()
        agendaProdutoAdapter.add(list)
    }


    private fun showFornecedorList(list: List<FornecedorModel>) {
        binding.pbLoading.hide()
        fornecedorAdapter.addAllItems(list)
    }

    private fun showRegisterFornecedorScreen() {
        startActivity(Intent(this@HomeActivity, FornecedorActivity::class.java))
        finish()
    }

    private fun showLoading() {
        binding.pbLoading.show()
    }

    private fun showEmptyList() {
        binding.pbLoading.hide()
        Snackbar.make(binding.root, "Não foi encontrado", Snackbar.LENGTH_SHORT).show()
    }


    private fun listarAgendaProduto(mes: String) {
        binding.rvList.adapter = agendaProdutoAdapter

        viewModel.listAgendaProdutoByMonth(mes, binding.root)
    }





    private fun showMiddleChoiceDIalog() {
        val dialog = MiddleDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
        val rdGroup = findViewById<RadioGroup>(R.id.rd_groupMiddle)
        rdGroup.setOnCheckedChangeListener { _, checkId ->
            when(checkId) {
                R.id.btn_pagamentoMiddle -> showRegisterAgendaPagamentoScreen()
                R.id.btn_produtoMiddle -> showRegisterAgendaProdutoScren()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAgendaDialog() {
        val dialog = AgendaDialogFragment()
        dialog.show(supportFragmentManager, dialog.tag)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterMonth(): LocalDate {
        val dateNow: LocalDate = LocalDate.now()
        val datefiltered = when (binding.monthFilter.selectedItem.toString()) {
            "Janeiro" -> dateNow.withMonth(1)
            "Fevereiro" -> dateNow.withMonth(2)
            "Março" -> dateNow.withMonth(3)
            "Abril" -> dateNow.withMonth(4)
            "Maio" -> dateNow.withMonth(5)
            "Junho" -> dateNow.withMonth(6)
            "Julho" -> dateNow.withMonth(7)
            "Agosto" -> dateNow.withMonth(8)
            "Setembro" -> dateNow.withMonth(9)
            "Outubro" -> dateNow.withMonth(10)
            "Novembro" -> dateNow.withMonth(11)
            "Dezembro" -> dateNow.withMonth(12)
            else -> dateNow
        }
        println("$datefiltered teste 1")
        return datefiltered
    }

    override fun onAgendaSelecionada(month: String) {
        viewModel.listAgendaProdutoByMonth(month, binding.root)
    }
}

