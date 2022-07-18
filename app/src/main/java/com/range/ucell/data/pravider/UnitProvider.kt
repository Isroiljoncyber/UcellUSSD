package com.range.ucell.data.pravider

import com.range.ucell.data.db.entity.Version


interface UnitProvider {

    suspend fun isOnline(): Boolean

    fun saveLoadDate()

    fun getSaveDate(): String

    fun saveLang(lang: String)

    fun getLang(): String

    fun saveReview(lang: Boolean)

    fun getReview(): Boolean

    fun saveVersion(version: String)

    fun getVersion(): String

    fun saveStatus(status: String)

    fun getStatus(): String
}