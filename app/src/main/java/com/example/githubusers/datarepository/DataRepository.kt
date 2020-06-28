package com.example.githubusers.datarepository

import com.example.githubusers.network.UsersApi
import com.example.githubusers.network.UsersApiService

object DataRepository {
    private lateinit var service: Any

    fun initFields() {
        if(!this::service.isInitialized) {
            service = UsersApi.retrofitService(UsersApiService::class.java)
        }
    }

}