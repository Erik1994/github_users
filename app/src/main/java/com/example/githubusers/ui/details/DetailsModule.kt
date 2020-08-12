package com.example.githubusers.ui.details

import com.example.githubusers.data.Users
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val detailsModule = module {

    viewModel { (users: Users) ->
        DetailsViewModel(users, get())
    }
}