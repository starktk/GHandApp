package com.example.ghandapp.agenda.agendaProduto.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaProduto.presentation.AgendaProdutoViewModel
import com.example.ghandapp.agenda.agendaProduto.presentation.model.AgendaProdutoViewState
import com.example.ghandapp.databinding.ActivityAgendaproductBinding
import com.example.ghandapp.extencoes.hide
import com.example.ghandapp.extencoes.show
import com.example.ghandapp.home.presentation.enums.StateStart
import com.example.ghandapp.home.view.HomeActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgendaProductActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAgendaproductBinding

    private val viewModel: AgendaProdutoViewModel by viewModels()
    private var selectedDate: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaproductBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.dateAgendaProd.setOnClickListener {
            openDialog { date ->
                selectedDate = date // Armazena a data quando o "OK" é clicado
            }
        }

        binding.registerAgendaProduto.setOnClickListener {
            if (selectedDate.isNotEmpty()) {
                viewModel.validateInputs(
                    nomeProduto = binding.nameProduct.text.toString(),
                    amount = binding.amount.text.toString().toInt(),
                    date = selectedDate,
                    cnpj = findViewById<EditText>(R.id.cnpjAgendaSet).text.toString(),
                    binding.root
                )
            } else {

                Toast.makeText(this, "Por favor, selecione uma data.", Toast.LENGTH_SHORT).show()
            }
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
            println("Data formatada: $formattedDate")
            onDateSelected(formattedDate)
        }
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return outputFormat.format(date)
    }
    private fun backHomePage() {
        startActivity(Intent(this@AgendaProductActivity, HomeActivity::class.java))
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