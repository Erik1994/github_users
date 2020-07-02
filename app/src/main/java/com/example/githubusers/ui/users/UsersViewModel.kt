package com.example.githubusers.ui.users

import androidx.lifecycle.*
import com.example.githubusers.data.Users
import com.example.githubusers.datarepository.DataRepository
import com.example.githubusers.network.enum.Status
import com.example.githubusers.network.resource.Resource
import kotlinx.coroutines.*

class UsersViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _usersResource = MutableLiveData<Resource<List<Users>>>()
    private val _userListMutable = MutableLiveData<List<Users>>()

    val usersResource: LiveData<Resource<List<Users>>>
        get() = _usersResource

    val userListLiveData: LiveData<List<Users>>
        get() = _userListMutable


    init {
        getUsersData()
    }

    fun setUsersList(usersList: List<Users>?) {
        _userListMutable.value = usersList
    }

    fun getUsersData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _usersResource.postValue(Resource(Status.LOADING, data = null, message = null))
                    val usersList = dataRepository.getUsers()
                    _usersResource.postValue(Resource(Status.SUCCESS, usersList, message = null))
                } catch (e: Throwable) {
                    _usersResource.postValue(
                        Resource(
                            Status.ERROR,
                            data = null,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

}