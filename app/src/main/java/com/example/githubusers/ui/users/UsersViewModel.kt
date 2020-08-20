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
    private val _navigateToDetailsMutable = MutableLiveData<Users>()

    val usersResource: LiveData<Resource<List<Users>>>
        get() = _usersResource

    val userListLiveData: LiveData<List<Users>>
        get() = _userListMutable

    val navigateToDetailsMutable: LiveData<Users>
        get() = _navigateToDetailsMutable


    init {
        getUsersData()
    }

    fun setUsersList(usersList: List<Users>?) {
        _userListMutable.value = usersList
    }

    fun naviGateToDetailsScreen(users: Users) {
        _navigateToDetailsMutable.value = users
    }

    fun navigateToDetailsScreenComplete() {
        _navigateToDetailsMutable.value = null
    }


    fun searchUsers(userName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _usersResource.postValue(Resource(Status.LOADING, null, null))
                    val searchUsers = dataRepository.searchUsers(userName)
                    _usersResource.postValue(Resource(Status.SUCCESS, searchUsers.items, null))

                } catch (t: Throwable) {
                    _usersResource.postValue(Resource(Status.ERROR, null, t.message))
                }
            }
        }
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