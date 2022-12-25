package com.joao01sb.tarefas

import android.app.Application
import com.joao01sb.tarefas.di.appDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(appDatabase)
        }
    }
}