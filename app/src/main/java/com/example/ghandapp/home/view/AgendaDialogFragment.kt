package com.example.ghandapp.home.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.ghandapp.R
import java.time.LocalDate

class AgendaDialogFragment: DialogFragment() {

    private var listener: AgendaValue? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dialog, container)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AgendaValue) {
            listener = context
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rdGroup = view.findViewById<RadioGroup>(R.id.rd_group)
        val button = view.findViewById<Button>(R.id.btn_submit)
        val spinner = view.findViewById<Spinner>(R.id.spinner_month)

        rdGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.btn_pagamento -> button.setOnClickListener {
                    // Lógica para pagamento
                }
                R.id.btn_produto -> button.setOnClickListener {
                    val month = setDateMonth(spinner)
                    dismiss()
                    println("Teste 1" + month)
                    listener?.onAgendaSelecionada(month.toString())
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDateMonth(spinner: Spinner): LocalDate {
        val dateNow: LocalDate = LocalDate.now()
        when (spinner.selectedItem.toString()) {
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
        }
        return dateNow
    }
    interface AgendaValue {
        fun onAgendaSelecionada(month: String)
    }
}