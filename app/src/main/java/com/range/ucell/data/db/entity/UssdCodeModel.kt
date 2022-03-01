package com.range.ucell.data.db.entity


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = "ussd_codes_table")
data class UssdCodeModel(
        @PrimaryKey(autoGenerate = false)
        @SerializedName("code")
        val code: String,
        @SerializedName("def")
        val def: String,
        @SerializedName("def_ru")
        val defRu: String
)