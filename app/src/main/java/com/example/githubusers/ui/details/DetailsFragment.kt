package com.example.githubusers.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.githubusers.R
import com.example.githubusers.data.Users
import com.example.githubusers.databinding.FragmentDetailsBinding
import com.example.githubusers.databinding.FragmentUsersBinding
import com.example.githubusers.network.enum.Status
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
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
                Status.LOADING -> print("Loading")
                Status.ERROR -> print(it.message)
                Status.SUCCESS -> model.setUser(it.data)
            }


        })
    }
}