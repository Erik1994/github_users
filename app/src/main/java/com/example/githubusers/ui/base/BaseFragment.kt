package com.example.githubusers.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.githubusers.R
import com.example.githubusers.ui.dialog.ErrorLoadingDialogFragment
import com.example.githubusers.util.ERROR_LOADING_DIALOG_KEY
import com.example.githubusers.util.ERROR_TYPE
import org.koin.androidx.viewmodel.ext.android.viewModel


open class BaseFragment : Fragment() {
    private lateinit var dialog: ErrorLoadingDialogFragment
    private val model: BaseViewModel by viewModel<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeNetwork()
    }
    private fun observeNetwork() {
        model.getNetworkLiveData().observe(this, Observer {
            if(!it) {
                openDialog(ERROR_TYPE)
            } else {
                closeDialog()
                reloadData()
            }
        })
    }

    fun openDialog(dialogType: String){
        dialog = ErrorLoadingDialogFragment.newInstance(dialogType)
        dialog.show(childFragmentManager, dialogType)

    }
    open fun reloadData() {}


    fun closeDialog() {
        if(this::dialog.isInitialized) {
           dialog.dismiss()
        }

    }


}