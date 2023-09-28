package com.example.ghandapp.fornecedor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FornecedorDao {

    @Insert
    fun insertFornecedor(fornecedorEntitiy: FornecedorEntitiy)

    @Query("DELETE from fornecedorTable")
    fun cleanFornecedor()
}