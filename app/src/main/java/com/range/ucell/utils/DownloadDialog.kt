package com.range.ucell.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wang.avi.AVLoadingIndicatorView
import com.range.ucell.R

class DownloadDialog() : DialogFragment() {

    private lateinit var loader: AVLoadingIndicatorView

    override fun getTheme(): Int {
        return R.style.RoundedCornersDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_download, container, false)
        loader = view.findViewById(R.id.loader)
        loader.show()
        return view
    }

    override fun onStop() {
        super.onStop()
        loader.hide()
    }
}