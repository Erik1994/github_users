package com.example.githubusers.ui.base

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModule = module {
    viewModel {
        BaseViewModel()
    }
}