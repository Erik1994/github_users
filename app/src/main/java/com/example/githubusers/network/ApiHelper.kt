package com.example.githubusers.network

class ApiHelper(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
    suspend fun getUser(login: String) = apiService.getUser(login)
    suspend fun searchUsers(userName: String) = apiService.searchUsers(userName)
}