package com.example.githubusers.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.data.Users
import com.example.githubusers.databinding.GridItemBinding

class PhotoGridAdapter(): androidx.recyclerview.widget.ListAdapter<Users, PhotoGridAdapter.ViewHolder>(DiffUtilCallBack)  {

    companion object DiffUtilCallBack: DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem.username == newItem.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }



    class ViewHolder(var binding: GridItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Users) {
            binding.users = user
            binding.executePendingBindings()
        }
    }


}