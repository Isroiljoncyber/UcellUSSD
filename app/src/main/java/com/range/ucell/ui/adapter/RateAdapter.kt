package com.range.ucell.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.range.ucell.App.Companion.sale
import com.skydoves.elasticviews.ElasticCardView
import com.range.ucell.R
import com.range.ucell.data.db.entity.RateModel
import com.range.ucell.ui.fragment.RateAction

/**
 * Created by Javoh on 16.08.2020.
 */

class RateAdapter(private val list: List<RateModel>, private val rAction: RateAction) : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    class RateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMothPay: AppCompatTextView = view.findViewById(R.id.mothPay)
        val tvMinutesTitle: AppCompatTextView = view.findViewById(R.id.minutes)
        val tvSmsTitle: AppCompatTextView = view.findViewById(R.id.sms)
        val tvMbCount: AppCompatTextView = view.findViewById(R.id.mbCount)
        val tvTitle: AppCompatTextView = view.findViewById(R.id.tvTitle)
        val tvMothPrice: AppCompatTextView = view.findViewById(R.id.tvMothPrice)
        val tvSms: AppCompatTextView = view.findViewById(R.id.tvSmsCount)
        val tvMinutes: AppCompatTextView = view.findViewById(R.id.tvMinutes)
        val tvTraffic: AppCompatTextView = view.findViewById(R.id.tvMbCount)
        val cardOver: ElasticCardView = view.findViewById(R.id.cardOver)
        val saleImage: AppCompatImageView = view.findViewById(R.id.saleItemRate)
        var list = if (sale != null) sale?.code!!.split("|") else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_rate_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        if (rAction.getLang()) {
            holder.tvTitle.text = list[position].nameRu
        } else holder.tvTitle.text = list[position].name

        if (sale != null && sale?.code != "no") {
            if (holder.list!!.contains(list[position].code)){
                holder.saleImage.visibility = View.VISIBLE
            } else {
                holder.saleImage.visibility = View.GONE
            }
        }

        holder.tvMothPrice.text = "${list[position].price} ${holder.tvMothPrice.context.getString(R.string.text_sum)}"
        holder.tvSms.text = "${list[position].sms} ${holder.tvSms.context.getString(R.string.text_sms)}"
        holder.tvMinutes.text = "${list[position].minutes} ${holder.tvMinutes.context.getString(R.string.text_minute_small)}"
        holder.tvTraffic.text = "${list[position].packet} ${holder.tvTraffic.context.getString(R.string.text_mb)}"

        if (list[position].type == "1") {
            holder.tvMothPay.text = "${holder.tvMothPay.text}\n${holder.itemView.context.getString(R.string.text_rate_type)}"
            holder.tvMinutesTitle.text = "${holder.tvMinutesTitle.text}\n${holder.itemView.context.getString(R.string.text_rate_type)}"
            holder.tvSmsTitle.text = "${holder.tvSmsTitle.text}\n${holder.itemView.context.getString(R.string.text_rate_type)}"
            holder.tvMbCount.text = "${holder.tvMbCount.text}\n${holder.itemView.context.getString(R.string.text_rate_type)}"
        } else {
            holder.tvMothPay.text = holder.itemView.context.getString(R.string.text_moth_pay) + " ${holder.itemView.context.getString(R.string.text_moth)}"
            holder.tvMinutesTitle.text = holder.itemView.context.getString(R.string.text_push_minutes)
            holder.tvSmsTitle.text = holder.itemView.context.getString(R.string.text_sms)
            holder.tvMbCount.text = holder.itemView.context.getString(R.string.text_traffic_count)
        }

        holder.cardOver.setOnClickListener { rAction.itemClick(list[position].code) }
    }
}