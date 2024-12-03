package com.example.ghandapp.fornecedor.view

import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao

class FornecedorViewHolder(
    private val binding: FornecedorListItemBinding,
    private val onStatusChange: (FornecedorModel) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    private val fornecedorUseCase by lazy {
        FornecedorUseCase()
    }
    fun bind(fornecedor: FornecedorModel) {
        binding.tvRazaoSocial.text = fornecedor.razaoSocial
        binding.tvCnpj.text = fornecedor.cnpj
        if (fornecedor.status?.equals(Situacao.ATIVA) == true) {
            binding.switchStatus.isChecked = false
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.green),
                PorterDuff.Mode.SRC_IN
            )
        } else if (fornecedor.status?.equals(Situacao.INATIVA) == true) {
            binding.switchStatus.isChecked = true
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.red),
                PorterDuff.Mode.SRC_IN
            )
        }
        binding.switchStatus.setOnCheckedChangeListener{_, isCheck ->
            if (isCheck) {
                fornecedor.status = Situacao.INATIVA
                binding.switchStatus.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.red),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                fornecedor.status = Situacao.ATIVA
                binding.switchStatus.trackDrawable?.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.green),
                    PorterDuff.Mode.SRC_IN
                )
            }
            onStatusChange(fornecedor)
        }
    }


}