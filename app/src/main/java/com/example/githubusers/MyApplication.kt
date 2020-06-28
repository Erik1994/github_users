package com.example.githubusers

import android.app.Application
import com.example.githubusers.datarepository.DataRepository
import com.example.githubusers.network.UsersApi

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        UsersApi.buildService()
        DataRepository.initFields()
    }
}