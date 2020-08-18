package com.example.githubusers.ui.webview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gitHubModule  = module {
    viewModel { (webUrl: String) -> GithubPageViewModel(webUrl) }
}