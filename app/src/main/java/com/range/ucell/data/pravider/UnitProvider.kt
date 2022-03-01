package com.range.ucell.data.pravider


interface UnitProvider {

    suspend fun isOnline(): Boolean

    fun saveLoadDate()

    fun getSaveDate(): String

    fun saveLang(lang: Boolean)

    fun getLang(): Boolean

    fun saveReview(lang: Boolean)

    fun getReview(): Boolean
}