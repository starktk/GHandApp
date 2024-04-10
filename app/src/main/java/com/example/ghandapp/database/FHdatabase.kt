package com.example.ghandapp.database

import com.example.ghandapp.App
import com.example.ghandapp.usuario.login.data.local.UserDao
import androidx.room.*
import com.example.ghandapp.fornecedor.data.local.FornecedorDao
import com.example.ghandapp.fornecedor.data.local.FornecedorEntitiy
import com.example.ghandapp.usuario.login.data.local.UserEntity

@Database(entities = [UserEntity::class, FornecedorEntitiy::class], version = 2)
abstract class FHdatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun fornecedorDao(): FornecedorDao


    companion object {
        fun getInstance(): FHdatabase {
            return Room.databaseBuilder(
                App.context,
                FHdatabase::class.java,
                "fh.database"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
    }
}