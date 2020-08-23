package com.example.githubusers.application

import com.example.githubusers.network.BASE_URL
import com.example.githubusers.network.createApiModule
import com.example.githubusers.ui.base.baseModule
import com.example.githubusers.ui.details.detailsModule
import com.example.githubusers.ui.users.usersModule
import com.example.githubusers.ui.webview.gitHubModule

val appComponents = listOf(createApiModule(BASE_URL), appModule, usersModule, detailsModule, gitHubModule, baseModule)