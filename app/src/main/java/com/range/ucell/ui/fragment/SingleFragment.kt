package com.range.ucell.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.range.ucell.App.Companion.sale
import com.skydoves.elasticviews.ElasticButton
import kotlinx.android.synthetic.main.fragment_single.*
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import com.range.ucell.R
import com.range.ucell.data.db.entity.MinutesModel
import com.range.ucell.data.db.entity.PacketModel
import com.range.ucell.data.repository.MobiuzRepository
import com.range.ucell.ui.adapter.MinutesAdapter
import com.range.ucell.ui.adapter.PacketsAdapter
import com.range.ucell.ui.base.ScopedFragment
import com.range.ucell.utils.UssdCodes
import com.range.ucell.utils.lazyDeferred
import com.range.ucell.utils.ussdCall

class SingleFragment(private val index: Int, private val isSMS: Boolean) :
    ScopedFragment(R.layout.fragment_single), SingleAction {

    private val mobiuzRepository: MobiuzRepository by instance()

    private var dialog: Dialog? = null
    private var btnOk: ElasticButton? = null
    private var dealerCode: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        avi.show()
        recyclerSingle.layoutManager = LinearLayoutManager(context)
        dialog = Dialog(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.dialog_ask)

        dialog?.findViewById<AppCompatTextView>(R.id.tvAsk)?.text =
            getString(R.string.confirm_service_ask)
        val btnCancel: ElasticButton = dialog?.findViewById(R.id.btnCancel)!!
        btnOk = dialog?.findViewById(R.id.btnOk)!!

        btnCancel.setOnClickListener { dialog?.dismiss() }

        if (!isSMS) {
            if (index == 3 || index == 4 || index == 7) {
                btnCheck.visibility = View.GONE
            }
        } else {
            // to gone button from sms
            if (index == 3) {
                btnCheck.visibility = View.GONE
            }
        }

        btnCheck.setOnClickListener {
            if (isSMS) {
                // In this case we will check the sms and minutes
                if (index == 0) {
                    ussdCall(UssdCodes.minuteCheck, it.context)
                } else {
                    when (index) {
                        1 -> ussdCall(UssdCodes.smsCheckDay, it.context)
                        2 -> ussdCall(UssdCodes.smsCheckMonth, it.context)
                    }
                }
            } else {
                // First of all we will check is it sms or no and then we make a call to them
                when (index) {
                    0 -> ussdCall(UssdCodes.packageMonth, it.context)
                    1 -> ussdCall(UssdCodes.packageMonth, it.context)
                    2 -> ussdCall(UssdCodes.packageMonth, it.context)
                    5 -> ussdCall(UssdCodes.packageTASIX, it.context)
                    6 -> ussdCall(UssdCodes.packageInfinity, it.context)
                }
            }
        }

        loadData()
        loadCode()
    }

    private fun loadData() = launch {
        if (isSMS) {
            lazyDeferred { mobiuzRepository.getMinutes() }.value.await()
                .observe(viewLifecycleOwner, Observer {
                    if (it == null) return@Observer
                    if (it.isEmpty()) return@Observer
                    bindMinutesUI(it)
                })
        } else {
            lazyDeferred { mobiuzRepository.getPackets() }.value.await()
                .observe(viewLifecycleOwner, Observer {
                    if (it == null) return@Observer
                    if (it.isEmpty()) return@Observer
                    bindPacketsUI(it)
                })
        }
    }

    private fun loadCode() = launch {
        lazyDeferred { mobiuzRepository.getDealerCode() }.value.await()
            .observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                dealerCode = it.code
            })
    }

    @SuppressLint("SetTextI18n")
    private fun bindPacketsUI(list: List<PacketModel>) {
        val model: ArrayList<PacketModel> = ArrayList()
        list.forEach {
            if (it.type == index) {
                model.add(it)
            }
        }
        recyclerSingle.adapter = PacketsAdapter(model, this)
        recyclerSingle.visibility = View.VISIBLE
        avi.hide()
        if (sale != null && sale?.sale == "1" && sale?.type == index.toString()) {
            tvSaleDate.visibility = View.VISIBLE
            if (unitProvider.getLang()) {
                tvSaleDate.text = "Aksiya ${sale?.dateIn} dan\n${sale?.dateFor} gacha"
            } else {
                tvSaleDate.text = "Акция с ${sale?.dateIn}\nдо ${sale?.dateFor}"
            }
        }
    }

    private fun bindMinutesUI(list: List<MinutesModel>) {
        val model: ArrayList<MinutesModel> = ArrayList()
        list.forEach {
            if (it.type == index) {
                model.add(it)
            }
        }
        recyclerSingle.adapter = MinutesAdapter(model, this, index)
        recyclerSingle.visibility = View.VISIBLE
        avi.hide()
        if (sale != null && sale?.sale == "2" && sale?.type == index.toString()) {
            tvSaleDate.visibility = View.VISIBLE
            if (unitProvider.getLang()) {
                tvSaleDate.text = "Aksiya ${sale?.dateIn} dan\n${sale?.dateFor} gacha"
            } else {
                tvSaleDate.text = "Акция с ${sale?.dateIn}\nдо ${sale?.dateFor}"
            }
        }
    }

    override fun itemClick(code: String) {
        if (dealerCode != null) {
            dialog?.show()
            btnOk?.setOnClickListener {
                if (isSMS) {
                    if (index == 1) {
                        ussdCall(code, it.context)
                    } else {
                        val ussd =
                            code + dealerCode + UssdCodes.encodedHash
                        ussdCall(ussd, it.context)
                    }
                } else {
                    val ussd = code + dealerCode + UssdCodes.encodedHash
                    ussdCall(ussd, it.context)
                }
                dialog?.dismiss()
            }
        } else loadCode()
    }
}

interface SingleAction {
    fun itemClick(code: String)
}