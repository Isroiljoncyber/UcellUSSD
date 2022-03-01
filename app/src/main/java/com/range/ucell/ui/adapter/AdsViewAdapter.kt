package com.range.ucell.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.skydoves.elasticviews.ElasticCardView
import com.range.ucell.R
import com.range.ucell.data.db.entity.BannerModel
import com.range.ucell.utils.MovingImageView

class AdsViewAdapter(private val list: List<BannerModel>): RecyclerView.Adapter<AdsViewAdapter.AdsViewHolder>() {

    class AdsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView: MovingImageView = view.findViewById(R.id.imgAds)
        val cardBanner: ElasticCardView = view.findViewById(R.id.cardBanner)
        val mView = view;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        return AdsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_ads_card_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        Glide.with(holder.mView)
                .load(list[position].image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView)
        holder.cardBanner.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(list[position].url)))
        }
    }
}