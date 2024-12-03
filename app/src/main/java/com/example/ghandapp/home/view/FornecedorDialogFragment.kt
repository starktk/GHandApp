package com.example.ghandapp.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.ghandapp.R

class FornecedorDialogFragment: DialogFragment() {
    var onSubmitClick: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fornecedor_dialog, container, false)

        val btnSubmit = view.findViewById<Button>(R.id.btn_submitFornecedor)
        val editText = view.findViewById<EditText>(R.id.etv_search_fornecedor)

        // Defina o comportamento do botão
        btnSubmit.setOnClickListener {
            val input = editText.text.toString() // Captura o texto inserido
            onSubmitClick?.invoke(input) // Chama o callback com o texto inserido
            dismiss() // Fecha o diálogo
        }

        return view
    }

    companion object {
        // Função para criar uma nova instância do diálogo
        fun newInstance(): FornecedorDialogFragment {
            return FornecedorDialogFragment()
        }
    }
}