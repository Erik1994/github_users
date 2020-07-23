package com.example.githubusers.ui.users

import androidx.paging.PageKeyedDataSource
import com.example.githubusers.data.Users
import kotlinx.coroutines.CoroutineScope

class UsersPagingDataSource(private val scope: CoroutineScope): PageKeyedDataSource<Int, Users>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Users>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Users>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Users>) {
        TODO("Not yet implemented")
    }


    companion object {
        const val PAGE_SIZE = 10
        const val FIRST_PAGE = 1
    }
}