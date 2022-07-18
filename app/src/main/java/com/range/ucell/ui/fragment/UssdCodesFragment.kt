package com.range.ucell.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.range.ucell.R
import com.range.ucell.data.db.entity.UssdCodeModel
import com.range.ucell.data.repository.MobiuzRepository
import com.range.ucell.ui.adapter.UssdAdapter
import com.range.ucell.ui.base.ScopedFragment
import com.range.ucell.utils.lazyDeferred
import kotlinx.android.synthetic.main.fragment_ussd_codes.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class UssdCodesFragment : ScopedFragment(R.layout.fragment_ussd_codes) {

    private val mobiuzRepository: MobiuzRepository by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
        avi.show()
        loadData()
    }

    private fun loadData() = launch {
        lazyDeferred { mobiuzRepository.getUssdCodes() }.value.await().observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            if (it.isEmpty()) return@Observer
            bindUI(it)
        })
    }

    private fun bindUI(list: List<UssdCodeModel>) {
        recyclerUssd.adapter = UssdAdapter(list, unitProvider.getLang()=="uz")
        avi.hide()
    }

}