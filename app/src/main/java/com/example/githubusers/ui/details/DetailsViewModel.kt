package com.example.githubusers.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.data.User
import com.example.githubusers.data.Users
import com.example.githubusers.datarepository.DataRepository
import com.example.githubusers.network.enum.Status
import com.example.githubusers.network.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val users: Users, private val dataRepository: DataRepository) :
    ViewModel() {
    private val _userResourceMutableLiveData = MutableLiveData<Resource<User>>()
    private val _userMutableLiveData = MutableLiveData<User>()
    private val _webUrlMutableLiveData = MutableLiveData<String>()

    val userResourceMutableLiveData: LiveData<Resource<User>>
        get() = _userResourceMutableLiveData

    val userMutableLiveData: LiveData<User>
        get() = _userMutableLiveData

    val webUrlLiveData: LiveData<String>
        get() = _webUrlMutableLiveData

    init {
        val login = users.username
        login?.let {
            getUserData(it)
        }
    }


    fun setUser(user: User?) {
        _userMutableLiveData.value = user
    }

    fun navigateToGithubPage(webUrl: String) {
        _webUrlMutableLiveData.value = webUrl
    }

    fun navigateToGithubPageComlate() {
        _webUrlMutableLiveData.value = null
    }



    private fun getUserData(login: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _userResourceMutableLiveData.postValue(Resource(Status.LOADING, null, null))
                    val user = dataRepository.getUser(login)
                    _userResourceMutableLiveData.postValue(Resource(Status.SUCCESS, user, null))
                } catch (e: Throwable) {
                    _userResourceMutableLiveData.postValue(Resource(Status.ERROR, null, e.message))
                }
            }
        }
    }


}