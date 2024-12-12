package com.example.ghandapp.fornecedor.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.example.ghandapp.fornecedor.presentation.enums.Situacao
import org.jetbrains.annotations.NotNull

@Entity(tableName = "fornecedorTable")
data class FornecedorEntity(
    @PrimaryKey
    val username: String,
    val fornecedores: List<FornecedorModel>?
)