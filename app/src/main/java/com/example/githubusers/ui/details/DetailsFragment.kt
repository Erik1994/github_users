package com.example.githubusers.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.githubusers.data.Users
import com.example.githubusers.databinding.FragmentDetailsBinding
import com.example.githubusers.network.enum.Status
import com.example.githubusers.ui.base.BaseFragment
import com.example.githubusers.util.ERROR_TYPE
import com.example.githubusers.util.LOADING_TYPE
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class DetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var users: Users
    private lateinit var model: DetailsViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            users = DetailsFragmentArgs.fromBundle(it).detailsUser
            model = getViewModel { parametersOf(users) }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(!this::binding.isInitialized) {
            binding = FragmentDetailsBinding.inflate(inflater)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = model
        bindData()
    }

    fun bindData() {
        model.userResourceMutableLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.LOADING -> openDialog(LOADING_TYPE)
                Status.ERROR -> openDialog(ERROR_TYPE)
                Status.SUCCESS -> {model.setUser(it.data)
                closeDialog()}
            }
        })

        model.webUrlLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToGithubPage(it))
                model.navigateToGithubPageComlate()
            }
        })
    }
}