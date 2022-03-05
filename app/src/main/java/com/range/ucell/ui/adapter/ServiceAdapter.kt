package com.range.ucell.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import at.blogc.android.views.ExpandableTextView
import com.skydoves.elasticviews.ElasticImageView
import com.skydoves.elasticviews.ElasticLayout
import com.range.ucell.R
import com.range.ucell.data.db.entity.ServiceModel
import com.range.ucell.ui.fragment.ServiceAction

/**
 * Created by Javoh on 16.08.2020.
 */

class ServiceAdapter(private val list: List<ServiceModel>, private val sAction: ServiceAction) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: AppCompatTextView = view.findViewById(R.id.tvTitle)
        val tvInfo: ExpandableTextView = view.findViewById(R.id.tvInfo)
        val btnCancel: ElasticLayout = view.findViewById(R.id.layoutCancel)
        val btnConnect: ElasticLayout = view.findViewById(R.id.layoutConnect)
        val btnDown: ElasticImageView = view.findViewById(R.id.imgDown)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_service_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        if (sAction.getLang()) {
            holder.tvTitle.text = list[position].name
            holder.tvInfo.text = list[position].def
        } else {
            holder.tvTitle.text = list[position].nameRu
            holder.tvInfo.text = list[position].defRu
        }

//        if (position in 3..6){
//            holder.btnCancel.visibility = View.VISIBLE
//        }

        holder.btnDown.setOnClickListener {
            if (holder.tvInfo.isExpanded){
                holder.tvInfo.collapse()
            }else holder.tvInfo.expand()
        }

        holder.btnCancel.setOnClickListener {
            sAction.itemCancelClick(position)
        }

        holder.btnConnect.setOnClickListener {
            sAction.itemConnectClick(list[position].code)
        }
    }
}