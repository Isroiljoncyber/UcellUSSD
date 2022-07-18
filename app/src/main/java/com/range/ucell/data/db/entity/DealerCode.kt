package com.range.ucell.data.db.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = "dealer_code_table")
data class DealerCode(
        @PrimaryKey(autoGenerate = false)
        val id: Int = 0,
        @SerializedName("code")
        val code: String,
        @SerializedName("centre_number")
        val centre_number: String
)