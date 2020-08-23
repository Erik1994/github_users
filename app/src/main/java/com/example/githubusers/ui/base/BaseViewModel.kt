package com.example.githubusers.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.util.NetworkUtil

class BaseViewModel: ViewModel() {

    fun getNetworkLiveData(): LiveData<Boolean> {
        return NetworkUtil.getNetWorkLiveData()
    }
}