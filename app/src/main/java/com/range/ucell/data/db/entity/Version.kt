package com.range.ucell.data.db.entity

import com.google.gson.annotations.SerializedName

data class Version(
    @SerializedName("data_ver")
    val data_ver: String,
    @SerializedName("sms_permission")
    val sms_permission: String
)