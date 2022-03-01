package com.range.ucell.data.db.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "lang_table")
data class LangModel(
        @PrimaryKey(autoGenerate = false)
        val id: Int = 0,
        val lang: Boolean
)