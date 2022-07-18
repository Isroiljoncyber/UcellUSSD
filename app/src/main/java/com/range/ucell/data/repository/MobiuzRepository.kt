package com.range.ucell.data.repository

import androidx.lifecycle.LiveData
import com.range.ucell.data.db.entity.*
import okhttp3.ResponseBody
import retrofit2.Call

interface MobiuzRepository {

    suspend fun fetchingAllData(): Boolean

    suspend fun getPackets(): LiveData<List<PacketModel>>

    suspend fun getMinutes(): LiveData<List<MinutesModel>>

    suspend fun getRate(): LiveData<List<RateModel>>

    suspend fun getServices(): LiveData<List<ServiceModel>>

    suspend fun getUssdCodes(): LiveData<List<UssdCodeModel>>

    suspend fun getDealerCode(): LiveData<DealerCode>

    suspend fun getSale(): SaleModel?

    suspend fun getBanners(): LiveData<List<BannerModel>>

    suspend fun getVersion(): Version?

    suspend fun sendTwilloSms(accountSID:String, sign: String, smsData: Map<String, String> ): Boolean
}