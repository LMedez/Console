package com.luc.console.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.luc.data.di.dataModule
import com.luc.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.luc.presentation.di.presentationModule

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(applicationContext)
            modules(dataModule + domainModule + presentationModule)
        }
    }
}