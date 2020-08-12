package com.example.githubusers.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentUsersBinding
import com.example.githubusers.network.ApiHelper
import com.example.githubusers.network.enum.Status
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel


class UsersFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding

//    private val viewModel: UsersViewModel by lazy {
//        ViewModelProvider(this, UsersViewModelFactory(ApiHelper(UsersApi.getRerofitService()))).get(
//            UsersViewModel::class.java
//        )
//    }
    private val model: UsersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (!this::binding.isInitialized) {
            binding = FragmentUsersBinding.inflate(inflater)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the UsersViewModel
        //val viewModelFactory = UsersViewModelFactory(ApiHelper(UsersApi.getRerofitService()))
        binding.viewModel = model//ViewModelProvider(this, viewModelFactory).get(UsersViewModel::class.java)
        binding.userRecyclerView.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            model.naviGateToDetailsScreen(it)
        })

        model.navigateToDetailsMutable.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToDetailsFragment(it))
                model.navigateToDetailsScreenComplete()
            }
        })
        bindData()
        coordinateMotion(view)
    }

    fun bindData() {
        model.usersResource.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.LOADING -> print("loading")
                Status.SUCCESS -> model.setUsersList(it.data)
                Status.ERROR -> print("error")
            }
        })
    }

    private fun coordinateMotion(view: View) {
        val appBarLayout: AppBarLayout = view.findViewById(R.id.appbar_layout)
        val motionLayout: MotionLayout = view.findViewById(R.id.motion_layout)

        val listener = AppBarLayout.OnOffsetChangedListener { unused, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }

        appBarLayout.addOnOffsetChangedListener(listener)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_bar_menu, menu)
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            menu.findItem(R.id.bar_share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bar_share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent(): Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(getString(R.string.share_success_text))
            .setType("text/plain")
            .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}