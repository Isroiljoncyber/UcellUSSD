package com.range.ucell.data.db.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = "sale_table")
data class SaleModel(
        @PrimaryKey(autoGenerate = false)
        var id: Int = 0,
        @SerializedName("sale")
        val sale: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("date_in")
        val dateIn: String,
        @SerializedName("date_for")
        val dateFor: String,
        @SerializedName("code")
        val code: String,
)