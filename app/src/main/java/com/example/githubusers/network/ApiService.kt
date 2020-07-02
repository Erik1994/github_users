package com.example.githubusers.network

import com.example.githubusers.data.User
import com.example.githubusers.data.Users
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/"


object UsersApi {
    private lateinit var retrofit: Retrofit

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun buildService() {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor)

        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttp.build())
            .baseUrl(BASE_URL)
            .build()
    }


    fun getRerofitService() = if(!this::retrofit.isInitialized) {
        buildService()
        retrofit.create(ApiService::class.java)
    } else {
        retrofit.create(ApiService::class.java)
    }


}


interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<Users>


    @GET("users/{login}")
    suspend fun getUser(@Query("login") login: String): User
}