package com.range.ucell.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.skydoves.elasticviews.ElasticButton
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import com.range.ucell.R
import com.range.ucell.data.db.entity.ServiceModel
import com.range.ucell.data.repository.MobiuzRepository
import com.range.ucell.ui.adapter.ServiceAdapter
import com.range.ucell.ui.base.ScopedFragment
import com.range.ucell.utils.UssdCodes
import com.range.ucell.utils.lazyDeferred
import com.range.ucell.utils.ussdCall

class ServiceFragment : ScopedFragment(R.layout.fragment_service), ServiceAction {

    private val mobiuzRepository: MobiuzRepository by instance()

    private var dialog1: Dialog? = null
    private var dialog2: Dialog? = null
    private var btnOk1: ElasticButton? = null
    private var btnOk2: ElasticButton? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog1 = Dialog(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        dialog1?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1?.setContentView(R.layout.dialog_ask)

        val btnCancel1: ElasticButton = dialog1?.findViewById(R.id.btnCancel)!!
        btnOk1 = dialog1?.findViewById(R.id.btnOk)!!

        btnCancel1.setOnClickListener { dialog1?.dismiss() }

        dialog2 = Dialog(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        dialog2?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog2?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog2?.setContentView(R.layout.dialog_ask)

        val btnCancel2: ElasticButton = dialog2?.findViewById(R.id.btnCancel)!!
        btnOk2 = dialog2?.findViewById(R.id.btnOk)!!

        btnCancel2.setOnClickListener { dialog1?.dismiss() }

        imgBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        avi.show()
        loadData()
    }

    private fun loadData() = launch {
        lazyDeferred { mobiuzRepository.getServices() }.value.await().observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            if (it.isEmpty()) return@Observer
            bindUI(it)
        })
    }

    private fun bindUI(list: List<ServiceModel>) {
        recyclerService.adapter = ServiceAdapter(list, this)
        avi.hide()

        btnCheck.setOnClickListener {
            if (unitProvider.getLang()) {
                ussdCall(UssdCodes.balanceUssdRu, it.context)
            } else
                ussdCall(UssdCodes.balanceUssdUz, it.context)
        }
    }

    override fun itemConnectClick(code: String) {
        dialog1?.findViewById<AppCompatTextView>(R.id.tvAsk)?.text = getString(R.string.confirm_service_ask)
        dialog1?.show()

        val c = code.substring(0, code.length - 1) + UssdCodes.encodedHash

        btnOk1?.setOnClickListener {
            dialog1?.dismiss()
            ussdCall(c, it.context)
        }
    }

    override fun itemCancelClick(id: Int) {
        dialog2?.findViewById<AppCompatTextView>(R.id.tvAsk)?.text = getString(R.string.confirm_cancel_service_ask)
        dialog2?.show()

        var code = ""
        when (id) {
            3 -> code = UssdCodes.holdCallCancel
            4 -> code = UssdCodes.missedCallCancel
            5 -> code = UssdCodes.antiAONCancel
            6 -> code = UssdCodes.lte4GCancel
        }

        btnOk2?.setOnClickListener {
            dialog2?.dismiss()
            ussdCall(code, it.context)
        }
    }

    override fun getLang(): Boolean {
        return unitProvider.getLang()
    }

}

interface ServiceAction {

    fun itemConnectClick(code: String)

    fun itemCancelClick(id: Int)

    fun getLang(): Boolean
}