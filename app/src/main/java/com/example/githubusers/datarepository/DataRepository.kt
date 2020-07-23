package com.example.githubusers.datarepository

import androidx.lifecycle.liveData
import com.example.githubusers.network.ApiHelper
import com.example.githubusers.network.resource.Resource
import kotlinx.coroutines.Dispatchers


class DataRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}