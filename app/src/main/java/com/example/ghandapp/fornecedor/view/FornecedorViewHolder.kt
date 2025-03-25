package com.example.ghandapp.fornecedor.view

import android.graphics.PorterDuff
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.R
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao


class FornecedorViewHolder(
    private val binding: FornecedorListItemBinding,
    private val onStatusChange: (FornecedorModel) -> Unit,
    private val onEditChange: (FornecedorModel, cnpj: String?) -> Unit,
    private val sendToWhatsapp: (FornecedorModel) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    private val originalHeight = binding.cardview.height
    fun bind(fornecedor: FornecedorModel) {
        binding.executePendingBindings()
        binding.tvRazaoSocial.setText(fornecedor.razaoSocial)
        binding.tvCnpj.setText(fornecedor.cnpj)
        binding.tvContactNumber.setText(fornecedor.contactNumber)
        binding.tvEletronicAddres.setText(fornecedor.eletronicAddres)
        if (fornecedor.status?.equals(Situacao.ATIVA) == true) {
            binding.switchStatus.isChecked = true
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.green),
                PorterDuff.Mode.SRC_IN
            )
        } else if (fornecedor.status?.equals(Situacao.INATIVA) == true) {
            binding.switchStatus.isChecked = false
            binding.switchStatus.trackDrawable?.setColorFilter(
                ContextCompat.getColor(binding.root.context, R.color.red),
                PorterDuff.Mode.SRC_IN
            )
        }

        permithedEdits(fornecedor)
        changeStatus(fornecedor)
        sendToWhatsapp(fornecedor)
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
            cardView.layoutParams.height = 760
            cardView.requestLayout()
            allowVisibilities(true)
            observeEdit(fornecedor, cardView)
        }
    }
    private fun observeEdit(fornecedor: FornecedorModel, cardView: CardView) {
            binding.btnSave.setOnClickListener {
                val razaoSocial = binding.tvRazaoSocial.text
                val cnpj = binding.tvCnpj.text
                val oldCnpj = fornecedor.cnpj
                allowVisibilities(false)
                cardView.layoutParams.height = originalHeight
                cardView.requestLayout()
                fornecedor.razaoSocial = razaoSocial.toString()
                fornecedor.cnpj = cnpj.toString()

                onEditChange(fornecedor, oldCnpj)
            }
            binding.btnCancel.setOnClickListener {
                cardView.layoutParams.height = originalHeight
                cardView.requestLayout()
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Atualiza o item no RecyclerView
                    (itemView.parent as RecyclerView).adapter?.notifyItemChanged(position)
                }
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
            binding.tvContactNumber.isClickable = true
            binding.tvContactNumber.isFocusable = true
            binding.tvContactNumber.isFocusableInTouchMode = true
            binding.tvEletronicAddres.isFocusable = true
            binding.tvEletronicAddres.isClickable = true
            binding.tvEletronicAddres.isFocusableInTouchMode = true
            binding.switchStatus.visibility = View.GONE
            binding.icWhatsapp.isClickable = false
            binding.icWhatsapp.isFocusable = false
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
            binding.tvContactNumber.isClickable = false
            binding.tvContactNumber.isFocusable = false
            binding.tvContactNumber.isFocusableInTouchMode = false
            binding.tvEletronicAddres.isFocusable = false
            binding.tvEletronicAddres.isClickable = false
            binding.tvEletronicAddres.isFocusableInTouchMode = false
            binding.icWhatsapp.isClickable = true
            binding.icWhatsapp.isFocusable = true
        }
    }

    private fun sendToWhatsapp(fornecedor: FornecedorModel) {
        binding.icWhatsapp.setOnClickListener {
            sendToWhatsapp(fornecedor)
        }
    }
}


