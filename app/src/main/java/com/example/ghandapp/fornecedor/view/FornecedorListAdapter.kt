package com.example.ghandapp.fornecedor.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.databinding.FornecedorListItemBinding
import com.example.ghandapp.fornecedor.data.model.FornecedorModel

class FornecedorListAdapter(private val onStatusChange: (FornecedorModel) -> Unit, private val onEditChange: (FornecedorModel, String?) -> Unit): RecyclerView.Adapter<FornecedorViewHolder>() {

    private val list: MutableList<FornecedorModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FornecedorViewHolder {
        val binding = FornecedorListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FornecedorViewHolder(binding, onStatusChange, onEditChange)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FornecedorViewHolder, position: Int) {
        holder.bind(list[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addAllItems(items: List<FornecedorModel>) {
        println(items)
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addSingleItem(items: FornecedorModel) {
        list.clear()
        list.add(items)
        notifyDataSetChanged()
    }
    fun getItemAtPosition(position: Int): FornecedorModel? {
        return if (position >= 0 && position < list.size) {
            list[position]
        } else {
            null
        }
    }
}