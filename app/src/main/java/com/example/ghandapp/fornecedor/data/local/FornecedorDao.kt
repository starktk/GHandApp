package com.example.ghandapp.fornecedor.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghandapp.fornecedor.data.local.FornecedorEntity

@Dao
interface FornecedorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFornecedores(fornecedorEntity: FornecedorEntity)

    @Query("SELECT * FROM fornecedorTable WHERE username = :username")
    fun getFornecedor(username: String): FornecedorEntity

    @Query("DELETE from fornecedorTable")
    fun cleanFornecedor()
}