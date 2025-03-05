package com.example.ghandapp.agenda.agendaProduto.view

import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.agenda.agendaProduto.data.local.AgendaProdutoModel
import com.example.ghandapp.agenda.agendaProduto.presentation.enums.SituacaoProduto
import com.example.ghandapp.databinding.AgendaprodutoListItemBinding
import com.example.ghandapp.fornecedor.data.model.FornecedorModel

class AgendaProdutoViewHolder(private val binding: AgendaprodutoListItemBinding,
                              private val onStatusChange: (AgendaProdutoModel) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(agenda: AgendaProdutoModel) {
        binding.nameProductCd.setText(agenda.nameProduct)
        binding.amountCdAgenda.setText(agenda.amount.toString())
        binding.dateCd.setText(agenda.date)
        binding.cnpjCdAgenda.setText(agenda.cnpj)
        binding.switchStatus.setText(agenda.situacaoProduto.toString())
        binding.executePendingBindings()

        if (agenda.situacaoProduto.equals(SituacaoProduto.RECEBIDO)) {
            binding.switchStatus.isChecked = false
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.green),
                PorterDuff.Mode.SRC_IN
            )
        } else if (agenda.situacaoProduto.equals(SituacaoProduto.NAO_RECEBIDO)) {
            binding.switchStatus.isChecked = true
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.red),
                PorterDuff.Mode.SRC_IN
            )
        }

        changeStatus(agenda)
    }

    fun permithedEdits() {

    }

    fun changeStatus(agendaProdutoModel: AgendaProdutoModel) {
        binding.switchStatus.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                agendaProdutoModel.situacaoProduto == SituacaoProduto.NAO_RECEBIDO
                binding.switchStatus.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.red),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                agendaProdutoModel.situacaoProduto == SituacaoProduto.RECEBIDO
                binding.switchStatus.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.green),
                    PorterDuff.Mode.SRC_IN
                )
            }
            onStatusChange(agendaProdutoModel)
        }
    }
}