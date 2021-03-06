package com.example.githubusers.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.githubusers.R
import com.example.githubusers.databinding.FragmentUsersBinding
import com.example.githubusers.network.enum.Status
import com.example.githubusers.ui.base.BaseFragment
import com.example.githubusers.util.ERROR_TYPE
import com.example.githubusers.util.LOADING_TYPE
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class UsersFragment : BaseFragment() {
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


    override fun reloadData() {
        super.reloadData()

        model.getUsersData()

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
        binding.viewModel =
            model//ViewModelProvider(this, viewModelFactory).get(UsersViewModel::class.java)
        binding.userRecyclerView.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            model.naviGateToDetailsScreen(it)
        })

        model.navigateToDetailsMutable.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(UsersFragmentDirections.actionUsersFragmentToDetailsFragment(it))
                model.navigateToDetailsScreenComplete()
            }
        })
        bindData()
        coordinateMotion(view)
    }

    fun bindData() {
        model.usersResource.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> openDialog(LOADING_TYPE)
                Status.SUCCESS -> {
                    model.setUsersList(it.data)
                    closeDialog()
                }
                Status.ERROR -> openDialog(ERROR_TYPE)
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

        val menuItem: MenuItem = menu.findItem(R.id.bar_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    searchUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null || newText.isEmpty()) {
                    model.getUsersData()
                }
                return true
            }

        })
    }

    private fun searchUsers(userName: String) {
        model.searchUsers(userName)
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