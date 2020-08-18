package com.example.githubusers.ui.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentGithubPageBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GithubPageFragment : Fragment() {
    private lateinit var binding: FragmentGithubPageBinding
    private lateinit var webUrl: String
    private lateinit var viewModel: GithubPageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            webUrl = GithubPageFragmentArgs.fromBundle(it).webUrl
            viewModel = getViewModel { parametersOf(webUrl)  }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(!this::binding.isInitialized) {
            binding = FragmentGithubPageBinding.inflate(inflater)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel
    }
}