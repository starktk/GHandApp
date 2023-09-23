package com.example.ghandapp.fornecedor.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fornecedorTable")
data class FornecedorEntitiy (
    @PrimaryKey
    val razaoSocial: String,
    val cnpj: Integer
        )