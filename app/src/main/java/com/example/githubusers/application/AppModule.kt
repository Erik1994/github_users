package com.example.githubusers.application


import com.example.githubusers.datarepository.DataRepository
import com.example.githubusers.network.ApiHelper
import org.koin.dsl.module

val appModule = module {

    single { DataRepository(get()) }
    single { ApiHelper(get()) }


}