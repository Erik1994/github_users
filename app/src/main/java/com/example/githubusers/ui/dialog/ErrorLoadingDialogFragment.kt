package com.example.githubusers.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.githubusers.R
import com.example.githubusers.util.ERROR_TYPE


private const val DIALOG_TYPE = "dialog type"

class ErrorLoadingDialogFragment : DialogFragment() {
    private var dialogType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dialogType = it.getString(DIALOG_TYPE)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            setContentView(R.layout.fragment_dialog)
            setCanceledOnTouchOutside(false)
            if(ERROR_TYPE == dialogType) {
                findViewById<ImageView>(R.id.network_error_image_view).visibility = View.VISIBLE
                findViewById<TextView>(R.id.loading_text).text = resources.getString(R.string.network_connection_error)
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
            } else {
                findViewById<ImageView>(R.id.network_error_image_view).visibility = View.GONE
                findViewById<TextView>(R.id.loading_text).text = resources.getString(R.string.loading_text)
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(dialogType: String) =
            ErrorLoadingDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_TYPE, dialogType)
                }
            }
    }
}