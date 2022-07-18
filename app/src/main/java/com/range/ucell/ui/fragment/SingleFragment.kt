package com.range.ucell.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.range.ucell.utils.SmsSendUtil
import com.range.ucell.utils.UssdCodes
import com.range.ucell.utils.lazyDeferred
import com.range.ucell.utils.ussdCall

class SingleFragment(
    private val index: Int,
    private val isSMS: Boolean,
    private val packageName: String = "",
    val autoChangeTab: AutoChangeTab? = null
) :
    ScopedFragment(R.layout.fragment_single), SingleAction {

    private val mobiuzRepository: MobiuzRepository by instance()

    private var dialog: Dialog? = null
    private lateinit var smsUtil: SmsSendUtil
    private var btnOk: ElasticButton? = null
    private var center_number: String? = null
    private lateinit var editText: AppCompatEditText;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsUtil = SmsSendUtil(requireContext());

        avi.show()
        recyclerSingle.layoutManager = LinearLayoutManager(context)
        dialog = Dialog(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(R.layout.dialog_ask)

        val btnCancel: ElasticButton = dialog?.findViewById(R.id.btnCancel)!!
        btnOk = dialog?.findViewById(R.id.btnOk)!!
        editText = dialog?.findViewById(R.id.edtPhoneNumber)!!
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

        editText.addTextChangedListener(object : TextWatcher {
            var lastChar = ""
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                val digits: Int = editText.text.toString().length
                if (digits > 1) lastChar = editText.text.toString().substring(digits - 1)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val digits: Int = editText.text.toString().length
                Log.d("LENGTH", "" + digits)
                if (lastChar != "-") {
                    if (digits == 2 || digits == 6 || digits == 9) {
                        editText.append("-")
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })


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
                center_number = it.centre_number;
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

        recyclerSingle.scrollToPosition(0)
        recyclerSingle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                RecyclerView.UNDEFINED_DURATION
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoChangeTab!!.onReachScrollEnd(true)
                }
//                else {
//                    autoChangeTab!!.onReachScrollEnd(false)
//                }
            }
        })
//        if (sale != null && sale?.sale == "1" && sale?.type == index.toString()) {
//            tvSaleDate.visibility = View.VISIBLE
//            if (unitProvider.getLang()) {
//                tvSaleDate.text = "Aksiya ${sale?.dateIn} dan\n${sale?.dateFor} gacha"
//            } else {
//                tvSaleDate.text = "Акция с ${sale?.dateIn}\nдо ${sale?.dateFor}"
//            }
//        }
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
//        if (sale != null && sale?.sale == "2" && sale?.type == index.toString()) {
//            tvSaleDate.visibility = View.VISIBLE
//            if (unitProvider.getLang()) {
//                tvSaleDate.text = "Aksiya ${sale?.dateIn} dan\n${sale?.dateFor} gacha"
//            } else {
//                tvSaleDate.text = "Акция с ${sale?.dateIn}\nдо ${sale?.dateFor}"
//            }
//        }
    }

    override fun itemClick(code: String, name: String) {
//        if (dealerCode != null) {
//            dialog?.show()
//            btnOk?.setOnClickListener {
//                if (isSMS) {
//                    if (index == 1) {
//                        ussdCall(code, it.context)
//                    } else {
//                        val ussd =
//                            code + dealerCode + UssdCodes.encodedHash
//                        ussdCall(ussd, it.context)
//                    }
//                } else {
//                    val ussd = code + dealerCode + UssdCodes.encodedHash
//                    ussdCall(ussd, it.context)
//                }
//                dialog?.dismiss()
//            }
//        } else loadCode()

        dialog?.findViewById<AppCompatTextView>(R.id.tvAsk)?.text =
            getString(R.string.confirm_service_ask)
        if (!isSMS && (index == 0 || index == 8)) {
            dialog?.findViewById<AppCompatTextView>(R.id.label)?.visibility = View.VISIBLE
            editText.visibility = View.VISIBLE
            editText.setText(String())
            dialog?.findViewById<AppCompatTextView>(R.id.tvAsk)?.text =
                packageName + name + "\n " +
                        getString(R.string.confirm_service_ask)
        }

        dialog?.show()
        btnOk?.setOnClickListener {
            if (isSMS) {
                if (index == 1) {
                    val ussd =
                        code + UssdCodes.encodedHash
                    ussdCall(ussd, it.context)
                }
            } else
                if (index == 0 || index == 8) {
                    val number = editText.text!!.toString().replace("-", "")
                    if (number.isNotEmpty() && number.length == 9 && number[0] == '9' && (number[1] == '3' || number[1] == '4')) {

                        val smsBody = "User number: $number\nPackage: $packageName\nName: $name"
                        smsUtil.sendSmsRequest(
                            smsBody,
                            "Buying this package",
                            centre_number = center_number!!
                        )
                        dialog?.dismiss()
                    } else {
                        editText.error = context?.resources?.getString(R.string.error_number);
                    }
                } else {
                    val ussd = code + UssdCodes.encodedHash
                    ussdCall(ussd, it.context)
                    dialog?.dismiss()
                }
        }
    }
}

interface AutoChangeTab {
    fun onReachScrollEnd(type: Boolean)
}

interface SingleAction {
    fun itemClick(code: String, name: String = "")
}