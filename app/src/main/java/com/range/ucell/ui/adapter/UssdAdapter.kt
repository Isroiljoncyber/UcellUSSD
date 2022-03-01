package com.range.ucell.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.range.ucell.R
import com.range.ucell.data.db.entity.UssdCodeModel
import com.range.ucell.utils.UssdCodes.Companion.encodedHash
import com.range.ucell.utils.ussdCall
import com.skydoves.elasticviews.ElasticCardView

class UssdAdapter(private val listModel: List<UssdCodeModel>, private val isLang: Boolean) : RecyclerView.Adapter<UssdAdapter.UssdViewHolder>() {

    class UssdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCode: AppCompatTextView = view.findViewById(R.id.tvUssd)
        val tvText: AppCompatTextView = view.findViewById(R.id.tvText)
        val cardCode: ElasticCardView = view.findViewById(R.id.cardUssd)
        val cardText: ElasticCardView = view.findViewById(R.id.cardText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UssdViewHolder {
        return UssdViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_ussd_codes_container, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UssdViewHolder, position: Int) {
        holder.tvCode.text = listModel[position].code+"#"
        holder.tvText.text = if (isLang) listModel[position].def else listModel[position].defRu

        holder.cardCode.setOnClickListener {
            val u: String = listModel[position].code+encodedHash
            ussdCall(u, it.context)
        }

        holder.cardText.setOnClickListener {
            val u: String = listModel[position].code+encodedHash
            ussdCall(u, it.context)
        }
    }

    override fun getItemCount(): Int {
        return listModel.size
    }
}