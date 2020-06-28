package com.example.githubusers.network

import com.example.githubusers.data.User
import com.example.githubusers.data.Users
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

interface UsersApiService {

    @GET("users")
    fun getUsers(): Deferred<List<Users>>


    @GET("users/{login}")
    fun getUser(@Query("login") login: String): Deferred<List<User>>
}


object UsersApi {
    private lateinit var retrofit: Retrofit;

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun buildService() {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor)

        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttp.build())
            .baseUrl(BASE_URL)
            .build()
    }

    fun <T> retrofitService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}
