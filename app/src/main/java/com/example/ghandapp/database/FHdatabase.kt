package com.example.ghandapp.database

import com.example.ghandapp.App
import com.example.ghandapp.login.data.local.UserDao
import androidx.room.*
import com.example.ghandapp.login.data.local.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class FHdatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

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