package com.example.ghandapp

import android.app.Application
import android.content.Context
import java.util.Locale


class App: Application(){

    companion object {
        private lateinit var instance: App
        val context: Context
            get() = instance
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        // Configura o idioma da aplicação para Português (Brasil)
        setLocale("pt", "BR")
    }

    // Método para definir a localidade
    private fun setLocale(language: String, country: String) {
        val locale = Locale(language, country)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}