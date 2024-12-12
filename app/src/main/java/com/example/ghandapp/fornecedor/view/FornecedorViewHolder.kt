package com.example.ghandapp.fornecedor.view

import android.graphics.PorterDuff
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.domain.FornecedorUseCase
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao

class FornecedorViewHolder(
    private val binding: FornecedorListItemBinding,
    private val onStatusChange: (FornecedorModel) -> Unit,
    private val onEditChange: (FornecedorModel, cnpj: String?) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    private val originalHeight = binding.cardview.height
    private val fornecedorUseCase by lazy {
        FornecedorUseCase()
    }
    fun bind(fornecedor: FornecedorModel) {

        binding.executePendingBindings()
        binding.tvRazaoSocial.setText(fornecedor.razaoSocial)
        binding.tvCnpj.setText(fornecedor.cnpj)
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
        permithedEdits(fornecedor)
        changeStatus(fornecedor)

    }

    fun changeStatus(fornecedor: FornecedorModel){
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
    private fun permithedEdits(fornecedor: FornecedorModel) {
        binding.editButton.setOnClickListener {
            val cardView = binding.cardview
            cardView.layoutParams.height = 530
            allowVisibilities(true)
            observeEdit(fornecedor, cardView)
        }
    }
    private fun observeEdit(fornecedor: FornecedorModel, carrView: CardView) {
            binding.btnSave.setOnClickListener {
                val razaoSocial = binding.tvRazaoSocial.text
                val cnpj = binding.tvCnpj.text
                val oldCnpj = fornecedor.cnpj
                allowVisibilities(false)
                carrView.layoutParams.height = originalHeight
                fornecedor.razaoSocial = razaoSocial.toString()
                fornecedor.cnpj = cnpj.toString()
                onEditChange(fornecedor, oldCnpj)
            }
            binding.btnCancel.setOnClickListener {
                allowVisibilities(false)
            }
    }
    private fun allowVisibilities(setVisibility: Boolean){
        if (setVisibility) {
            binding.tvRazaoSocial.isFocusable = true
            binding.tvRazaoSocial.isClickable = true
            binding.tvRazaoSocial.isFocusableInTouchMode = true
            binding.tvCnpj.isFocusable = true
            binding.tvCnpj.isClickable = true
            binding.tvCnpj.isFocusableInTouchMode = true
            binding.btnSave.visibility = View.VISIBLE
            binding.btnCancel.visibility = View.VISIBLE
            binding.switchStatus.visibility = View.GONE
        } else {
            binding.switchStatus.visibility = View.VISIBLE
            binding.btnSave.visibility = View.GONE
            binding.btnCancel.visibility = View.GONE
            binding.tvRazaoSocial.isFocusable = false
            binding.tvRazaoSocial.isClickable = false
            binding.tvRazaoSocial.isFocusableInTouchMode = false
            binding.tvCnpj.isFocusable = false
            binding.tvCnpj.isClickable = false
            binding.tvCnpj.isFocusableInTouchMode = false
        }
    }
}


