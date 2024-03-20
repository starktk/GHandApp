package com.example.ghandapp.fornecedor.view

import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.SituacaoFornecedor

class FornecedorViewHolder(private val binding: FornecedorListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(fornecedor: FornecedorModel) {
        binding.tvRazaoSocial.text = fornecedor.razaoSocial
        binding.tvCnpj.text = fornecedor.cnpj
        if (fornecedor.status == SituacaoFornecedor.Ativa.toString()) {
            binding.btnStatus.setBackgroundResource(R.color.green)
        } else if (fornecedor.status == SituacaoFornecedor.Inativa.toString()) {
            binding.btnStatus.setBackgroundResource(R.color.red)
        }

    }
}