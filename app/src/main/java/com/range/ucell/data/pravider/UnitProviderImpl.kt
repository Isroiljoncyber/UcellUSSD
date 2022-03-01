package com.range.ucell.data.pravider

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.range.ucell.data.db.MobiuzDao
import java.net.InetAddress
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

class UnitProviderImpl(private val context: Context, private val mobiuzDao: MobiuzDao) : UnitProvider, PreferenceProvider(context) {

    private val saveDate = "SAVE_DATA"
    private val language = "LANGUAGE"
    private val review = "REVIEW"

    override suspend fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return if (networkInfo != null && networkInfo.isConnected) {
            try {
                !InetAddress.getByName("google.com").equals("")
            } catch (e: UnknownHostException) {
                false
            }
        } else false
    }

    @SuppressLint("SimpleDateFormat")
    override fun saveLoadDate() {
        preferences.edit().putString(saveDate, SimpleDateFormat("dd.MM.yy").format(Date())).apply()
    }

    override fun getSaveDate(): String {
        return preferences.getString(saveDate, "not")!!
    }

    override fun saveLang(lang: Boolean) {
        preferences.edit().putBoolean(language, lang).apply()
    }

    override fun getLang(): Boolean {
        return preferences.getBoolean(language, false)?:false
    }

    override fun saveReview(re: Boolean) {
        preferences.edit().putBoolean(review, re).apply()
    }

    override fun getReview(): Boolean {
        return preferences.getBoolean(review, true)
    }

}