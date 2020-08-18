package com.example.githubusers.ui.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GithubPageViewModel(private val url: String) : ViewModel() {
    private val _webUrlMutableLiveData = MutableLiveData<String>()
    val webUrlMutableLiveData: LiveData<String>
        get() = _webUrlMutableLiveData

    init {
        _webUrlMutableLiveData.value = url
    }
}