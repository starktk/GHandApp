package com.example.ghandapp.agenda.agendaPagamento.view

import android.graphics.PorterDuff
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaPagamento.data.local.AgendaPagamentoModel
import com.example.ghandapp.agenda.agendaPagamento.data.local.SituacaoPagamento
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.databinding.AgendapagamentoListItemBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AgendaPagamentoViewHolder(private val binding: AgendapagamentoListItemBinding,
                                private val onStatusChange: (AgendaPagamentoModel) -> Unit): RecyclerView.ViewHolder(binding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(agenda: AgendaPagamentoModel) {
        val date = convertDate(agenda.dateToPayOrReceive)
        binding.valueToPay.setText(agenda.valueToPay.toString())
        binding.dateYear.text = date.year.toString()
        binding.dateMonth.text = showDate(date)
        binding.cnpjCdAgenda.setText(agenda.cnpj)
        binding.executePendingBindings()
        if (agenda.status.equals(SituacaoProduto.RECEBIDO)) {
            binding.switchStatus.isChecked = false
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.green),
                PorterDuff.Mode.SRC_IN
            )
        } else if (agenda.status.equals(SituacaoProduto.NAO_RECEBIDO)) {
            binding.switchStatus.isChecked = true
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.red),
                PorterDuff.Mode.SRC_IN
            )
        }
        changeStatus(agenda)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(date, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDate(date: LocalDate): String {
        var month = ""
        when (date.monthValue) {
            1 -> month = "Janeiro"
            2 -> month = "Fevereiro"
            3 -> month = "Março"
            4 -> month = "Abril"
            5 -> month = "Maio"
            6 -> month = "Junho"
            7 -> month = "Julho"
            8 -> month = "Agosto"
            9 -> month = "Setembro"
            10 -> month = "Outubro"
            11 -> month = "Novembro"
            12 -> month = "Dezembro"
        }
        if (month == "Dezembro") {
            month = month + ", " + date.dayOfMonth.toString()
            println(date.dayOfMonth)
            println(date.monthValue)
            println(month)
        }

        println("Month: $month, Day: ${date.dayOfMonth}")
        month = month + ", " + date.dayOfMonth.toString()
        println(month)

        return month
    }

    private fun changeStatus(agenda: AgendaPagamentoModel) {
        binding.switchStatus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                agenda.status == SituacaoPagamento.A_PAGAR.toString()
                binding.switchStatus.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.red),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                agenda.status == SituacaoProduto.RECEBIDO.toString()
                binding.switchStatus.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.green),
                    PorterDuff.Mode.SRC_IN
                )
            }
            onStatusChange(agenda)
        }
    }
}