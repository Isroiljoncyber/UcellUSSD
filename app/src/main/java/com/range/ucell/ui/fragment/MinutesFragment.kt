package com.range.ucell.ui.fragment

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_minutes.*
import com.range.ucell.R
import com.range.ucell.ui.adapter.FragmentAdapter

class MinutesFragment : Fragment(R.layout.fragment_minutes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI(){
        val adapter = FragmentAdapter(childFragmentManager)
        adapter.addFragment(SingleFragment(0, true), getString(R.string.text_minute))
        adapter.addFragment(SingleFragment(1, true), getString(R.string.text_sms))
        viewPagerNet.adapter = adapter
        tabs.setViewPager(viewPagerNet)

        imgBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        imgInfo.setOnClickListener {
            if (layoutInfo.visibility == View.GONE){
                layoutInfo.visibility = View.VISIBLE
                tvInfo.text = Html.fromHtml(getString(R.string.minute_text_info))
            }else layoutInfo.visibility =View.GONE
        }
    }
}