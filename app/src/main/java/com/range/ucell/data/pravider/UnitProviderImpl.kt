package com.range.ucell.data.pravider

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.range.ucell.data.db.MobiuzDao
import com.range.ucell.data.db.entity.Version
import java.net.InetAddress
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

class UnitProviderImpl(private val context: Context, private val mobiuzDao: MobiuzDao) :
    UnitProvider, PreferenceProvider(context) {

    private val saveDate = "SAVE_DATA"
    private val language = "LANGUAGE"
    private val review = "REVIEW"
    private val version = "VERSION"
    private val status = "STATUS"

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

    override fun saveLang(lang: String) {
        preferences.edit().putString(language, lang).apply()
    }

    override fun getLang(): String {
        return preferences.getString(language, "")!!
    }

    override fun saveReview(re: Boolean) {
        preferences.edit().putBoolean(review, re).apply()
    }

    override fun getReview(): Boolean {
        return preferences.getBoolean(review, true)
    }

    override fun saveVersion(version: String) {
        preferences.edit().putString(this.version, version).apply()
    }

    override fun getVersion(): String {
        return preferences.getString(version, "0")!!;
    }

    override fun saveStatus(status: String) {
        preferences.edit().putString(this.status, status).apply()
    }

    override fun getStatus(): String {
        return preferences.getString(status, "0")!!;
    }

}