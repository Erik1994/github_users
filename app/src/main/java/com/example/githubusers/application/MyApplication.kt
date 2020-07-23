package com.example.githubusers.application

import android.app.Application
import com.example.githubusers.datarepository.DataRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@MyApplication)

            modules(provideComponent())
        }
    }

    private fun provideComponent() = appComponents
}