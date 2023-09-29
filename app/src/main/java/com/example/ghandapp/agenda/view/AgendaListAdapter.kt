package com.example.ghandapp.agenda.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ghandapp.databinding.AgendaItemBinding

class AgendaListAdapter: RecyclerView.Adapter<AgendaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaViewHolder {
        val binding = AgendaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgendaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AgendaViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}