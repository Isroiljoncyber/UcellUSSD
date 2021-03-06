package com.range.ucell.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.Window
import androidx.navigation.Navigation
import com.range.ucell.App.Companion.sale
import com.skydoves.elasticviews.ElasticLayout
import kotlinx.android.synthetic.main.fragment_internet.*
import com.range.ucell.R
import com.range.ucell.ui.adapter.FragmentAdapter
import com.range.ucell.utils.UssdCodes
import com.range.ucell.utils.ussdCall

class InternetFragment : Fragment(R.layout.fragment_internet), AutoChangeTab {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        val adapter = FragmentAdapter(childFragmentManager)
        if (sale != null) {
            when (sale?.type) {
                "0" -> {
                    adapter.addFragment(
                        SingleFragment(
                            0,
                            false,
                            getString(R.string.text_month_packets), this
                        ), getString(R.string.text_month_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(
                            8,
                            false,
                            getString(R.string.text_month_packets_newSof), this
                        ), getString(R.string.text_month_packets_newSof)
                    )
                    adapter.addFragment(
                        SingleFragment(1, false, autoChangeTab = this),
                        getString(R.string.text_week_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(2, false, autoChangeTab = this),
                        getString(R.string.text_day_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(3, false, autoChangeTab = this),
                        getString(R.string.text_night_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(4, false, autoChangeTab = this),
                        getString(R.string.text_hour_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(5, false, autoChangeTab = this),
                        getString(R.string.text_tax_ix_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(6, false, autoChangeTab = this),
                        getString(R.string.text_infinite_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(7, false, autoChangeTab = this),
                        getString(R.string.text_modem_packets)
                    )
                }
                "1" -> {
                    adapter.addFragment(
                        SingleFragment(0, false),
                        getString(R.string.text_month_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(1, false),
                        getString(R.string.text_week_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(2, false),
                        getString(R.string.text_day_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(3, false),
                        getString(R.string.text_night_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(4, false),
                        getString(R.string.text_hour_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(5, false),
                        getString(R.string.text_tax_ix_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(6, false),
                        getString(R.string.text_infinite_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(7, false),
                        getString(R.string.text_modem_packets)
                    )
                }
                "2" -> {
                    adapter.addFragment(
                        SingleFragment(0, false),
                        getString(R.string.text_month_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(1, false),
                        getString(R.string.text_week_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(2, false),
                        getString(R.string.text_day_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(3, false),
                        getString(R.string.text_night_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(4, false),
                        getString(R.string.text_hour_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(5, false),
                        getString(R.string.text_tax_ix_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(6, false),
                        getString(R.string.text_infinite_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(7, false),
                        getString(R.string.text_modem_packets)
                    )
                }
                "4" -> {
                    adapter.addFragment(
                        SingleFragment(0, false),
                        getString(R.string.text_month_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(1, false),
                        getString(R.string.text_week_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(2, false),
                        getString(R.string.text_day_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(3, false),
                        getString(R.string.text_night_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(4, false),
                        getString(R.string.text_hour_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(5, false),
                        getString(R.string.text_tax_ix_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(6, false),
                        getString(R.string.text_infinite_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(7, false),
                        getString(R.string.text_modem_packets)
                    )
                }
                "5" -> {
                    adapter.addFragment(
                        SingleFragment(0, false),
                        getString(R.string.text_month_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(1, false),
                        getString(R.string.text_week_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(2, false),
                        getString(R.string.text_day_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(3, false),
                        getString(R.string.text_night_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(4, false),
                        getString(R.string.text_hour_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(5, false),
                        getString(R.string.text_tax_ix_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(6, false),
                        getString(R.string.text_infinite_packets)
                    )
                    adapter.addFragment(
                        SingleFragment(7, false),
                        getString(R.string.text_modem_packets)
                    )
                }
            }
        } else {
            adapter.addFragment(SingleFragment(0, false), getString(R.string.text_month_packets))
            adapter.addFragment(SingleFragment(1, false), getString(R.string.text_week_packets))
            adapter.addFragment(SingleFragment(2, false), getString(R.string.text_day_packets))
            adapter.addFragment(SingleFragment(3, false), getString(R.string.text_night_packets))
            adapter.addFragment(SingleFragment(4, false), getString(R.string.text_hour_packets))
            adapter.addFragment(SingleFragment(5, false), getString(R.string.text_tax_ix_packets))
            adapter.addFragment(SingleFragment(6, false), getString(R.string.text_infinite_packets))
            adapter.addFragment(SingleFragment(7, false), getString(R.string.text_modem_packets))
        }

        viewPagerNet.adapter = adapter
        tabs.setViewPager(viewPagerNet)

        imgBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        val dialog = Dialog(requireContext(), R.style.Theme_AppCompat_Light_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_config)

        val getConfig: ElasticLayout = dialog.findViewById(R.id.tvGetConfigs)
        val onNet: ElasticLayout = dialog.findViewById(R.id.tvOnNet)
        val offNet: ElasticLayout = dialog.findViewById(R.id.tvOffNet)
        val offOnNet: ElasticLayout = dialog.findViewById(R.id.tvOffOnNet)

        getConfig.setOnClickListener {
            ussdCall(UssdCodes.getSettings, it.context)
        }

        onNet.setOnClickListener {
            ussdCall(UssdCodes.turnOnMobileNet, it.context)
        }

        offNet.setOnClickListener {
            ussdCall(UssdCodes.turnOffMobileNet, it.context)
        }

        offOnNet.setOnClickListener {
            ussdCall(UssdCodes.turnOffOnNet, it.context)
        }

        imgConfig.setOnClickListener {
            dialog.show()
        }
    }

    var index = 0;
    override fun onReachScrollEnd(type: Boolean) {
        index = viewPagerNet.currentItem
//        if (type) {
        if (index < 4)
            ++index
        else {
            index = 0
        }
//        } else {
//            if (index > 1)
//                --index
//            else {
//                index = 8
//            }
//        }
        viewPagerNet.setCurrentItem(index, true)
    }
}