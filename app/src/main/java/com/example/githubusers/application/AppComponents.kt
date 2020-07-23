package com.example.githubusers.application

import com.example.githubusers.network.BASE_URL
import com.example.githubusers.network.createApiModule
import com.example.githubusers.ui.users.usersModule

val appComponents = listOf(createApiModule(BASE_URL), appModule, usersModule)