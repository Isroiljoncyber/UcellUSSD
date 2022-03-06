package com.range.ucell.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.range.ucell.App.Companion.sale
import com.skydoves.elasticviews.ElasticCardView
import com.range.ucell.R
import com.range.ucell.data.db.entity.PacketModel
import com.range.ucell.ui.fragment.SingleAction
import com.range.ucell.utils.UssdCodes
import com.range.ucell.utils.UssdCodes.Companion.dealerCodeHash

/**
 * Created by Javoh on 15.08.2020.
 */

class PacketsAdapter(private val list: List<PacketModel>, private val sAction: SingleAction) :
    RecyclerView.Adapter<PacketsAdapter.PacketsViewHolder>() {

    class PacketsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvValue: AppCompatTextView = view.findViewById(R.id.tvValue)
        val tvPrice: AppCompatTextView = view.findViewById(R.id.tvPrice)
        val tvCode: AppCompatTextView = view.findViewById(R.id.tvCode)
        val cardBuy: ElasticCardView = view.findViewById(R.id.cardBuy)
        val saleImage: AppCompatImageView = view.findViewById(R.id.saleItemSingle)
        val imgShare: AppCompatImageView = view.findViewById(R.id.imgShare)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacketsViewHolder {
        return PacketsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_single_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PacketsViewHolder, position: Int) {
        holder.tvValue.isSelected = true
        holder.tvCode.isSelected = true
        if (list[position].type == 2) {
            holder.tvValue.text =
                "${list[position].name} ${holder.tvValue.context.getString(R.string.text_day)}"
        } else holder.tvValue.text =
            "${list[position].name} ${holder.tvValue.context.getString(R.string.text_mb)}"

        if (sale != null && sale?.sale == "1" && sale?.type == list[position].type.toString()) {
            holder.saleImage.visibility = View.VISIBLE
        }

        holder.tvPrice.text = list[position].price

        holder.cardBuy.setOnClickListener { sAction.itemClick(list[position].code) }

//        holder.tvCode.text = "${holder.itemView.context.getString(R.string.text_code)} ${UssdCodes.netPackets + list[position].code + dealerCodeHash}"
        // Some other codes don't need diller code so check it todo

        holder.tvCode.text =
            "${holder.itemView.context.getString(R.string.text_code)} ${list[position].code + "#"}"

        holder.imgShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                holder.itemView.context.getString(R.string.app_name)
            )
//            val message = UssdCodes.netPackets + list[position].code + dealerCodeHash
            val message = list[position].code + "#"
            intent.putExtra(Intent.EXTRA_TEXT, message)
            holder.itemView.context.startActivity(Intent.createChooser(intent, "Ucell"))
        }
    }
}