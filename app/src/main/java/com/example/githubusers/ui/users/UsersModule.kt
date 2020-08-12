package com.example.githubusers.ui.users

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val usersModule = module {

    viewModel { UsersViewModel(get()) }

}