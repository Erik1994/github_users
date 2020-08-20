package com.example.githubusers.network

import com.example.githubusers.data.SearchUsers
import com.example.githubusers.data.User
import com.example.githubusers.data.Users
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<Users>

    @GET("search/users")
    suspend fun searchUsers(@Query("q") username:String): SearchUsers


    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): User
}