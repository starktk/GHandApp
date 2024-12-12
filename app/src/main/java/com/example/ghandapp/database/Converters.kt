package com.example.ghandapp.database

import androidx.room.TypeConverter
import com.example.ghandapp.fornecedor.data.local.FornecedorEntity
import com.example.ghandapp.fornecedor.data.model.FornecedorModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    private val moshi = Moshi.Builder().build()

    // Converte a lista de FornecedorEntity para JSON
    @TypeConverter
    fun fromFornecedorEntityList(fornecedores: List<FornecedorEntity>?): String? {
        val type = Types.newParameterizedType(List::class.java, FornecedorEntity::class.java)
        val adapter = moshi.adapter<List<FornecedorEntity>>(type)
        return adapter.toJson(fornecedores)
    }

    // Converte o JSON de volta para a lista de FornecedorEntity
    @TypeConverter
    fun toFornecedorEntityList(itemsString: String?): List<FornecedorEntity>? {
        val type = Types.newParameterizedType(List::class.java, FornecedorEntity::class.java)
        val adapter = moshi.adapter<List<FornecedorEntity>>(type)
        return adapter.fromJson(itemsString)
    }

    // Converte a lista de FornecedorModel para JSON
    @TypeConverter
    fun fromFornecedorModelList(fornecedores: List<FornecedorModel>?): String? {
        val type = Types.newParameterizedType(List::class.java, FornecedorModel::class.java)
        val adapter = moshi.adapter<List<FornecedorModel>>(type)
        return adapter.toJson(fornecedores)
    }

    // Converte o JSON de volta para a lista de FornecedorModel
    @TypeConverter
    fun toFornecedorModelList(itemsString: String?): List<FornecedorModel>? {
        val type = Types.newParameterizedType(List::class.java, FornecedorModel::class.java)
        val adapter = moshi.adapter<List<FornecedorModel>>(type)
        return adapter.fromJson(itemsString)
    }
}