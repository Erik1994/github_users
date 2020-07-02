package com.example.githubusers

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubusers.data.Users
import com.example.githubusers.network.enum.Status
import com.example.githubusers.network.resource.Resource
import com.example.githubusers.ui.users.PhotoGridAdapter

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}

@BindingAdapter("usersList")
fun RecyclerView.bindRecyclerView(userList: List<Users>?) {
    userList?.let {
        val adapter = this.adapter as PhotoGridAdapter
        adapter.submitList(it)
    }
}