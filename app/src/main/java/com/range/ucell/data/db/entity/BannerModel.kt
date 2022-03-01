package com.range.ucell.data.db.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = "banner_table")
data class BannerModel(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @SerializedName("image")
        val image: String,
        @SerializedName("url")
        val url: String
)