package com.range.ucell.data.repository

import androidx.lifecycle.LiveData
import com.range.ucell.App.Companion.sale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.range.ucell.data.db.MobiuzDao
import com.range.ucell.data.db.entity.*
import com.range.ucell.data.network.ApiService
import com.range.ucell.data.pravider.UnitProvider

class MobiuzRepositoryImpl( private val mobiuzDao: MobiuzDao, private val unitProvider: UnitProvider, private val apiService: ApiService)
    : MobiuzRepository {

    override suspend fun getPackets(): LiveData<List<PacketModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getPackets()
        }
    }

    override suspend fun getMinutes(): LiveData<List<MinutesModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getMinutes()
        }
    }

    override suspend fun getRate(): LiveData<List<RateModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getRate()
        }
    }

    override suspend fun getServices(): LiveData<List<ServiceModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getService()
        }
    }

    override suspend fun getUssdCodes(): LiveData<List<UssdCodeModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getUssdCodes()
        }
    }

    override suspend fun getDealerCode(): LiveData<DealerCode> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getCode()
        }
    }

    override suspend fun getSale(): SaleModel? {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getSale()
        }
    }

    override suspend fun getBanners(): LiveData<List<BannerModel>> {
        return withContext(Dispatchers.IO) {
            return@withContext mobiuzDao.getBanner()
        }
    }

    override suspend fun fetchingAllData(): Boolean {
        var isLoaded = true
        try {
            val resBanner = apiService.getBanners()
            if (resBanner.isSuccessful) {
                resBanner.body()!!.forEach {
                    mobiuzDao.upsertBanner(it)
                }
            } else isLoaded = false

            val resSale = apiService.getSaleAsync()
            if (resSale.isSuccessful) {
                if (resSale.body()?.sale != "no") {
                    sale = resSale.body()
                    mobiuzDao.upsertSale(resSale.body()!!)
                } else {
                    sale = null
                    mobiuzDao.deleteSale()
                }
            } else isLoaded = false

            val resCode = apiService.getDealerCode()
            if (resCode.isSuccessful) {
                mobiuzDao.upsertCode(resCode.body()!!)
            } else isLoaded = false

            val response = apiService.getPacketsAsync()
            if (response.isSuccessful && response.body()!!.isNotEmpty()) {
                mobiuzDao.deletePackets()
                response.body()!!.forEach {
                    mobiuzDao.upsertPackets(it)
                }
            } else isLoaded = false

            val response2 = apiService.getMinutesAsync()
            if (response2.isSuccessful && response2.body()!!.isNotEmpty()) {
                mobiuzDao.deleteMinutes()
                response2.body()!!.forEach {
                    mobiuzDao.upsertMinutes(it)
                }
            } else isLoaded = false

            val response3 = apiService.getRateAsync()
            if (response3.isSuccessful && response3.body()!!.isNotEmpty()) {
                mobiuzDao.deleteRate()
                response3.body()!!.forEach {
                    mobiuzDao.upsertRate(it)
                }
            } else isLoaded = false

            val response4 = apiService.getServicesAsync()
            if (response4.isSuccessful && response4.body()!!.isNotEmpty()) {
                mobiuzDao.deleteService()
                response4.body()!!.forEach {
                    mobiuzDao.upsertService(it)
                }
            } else isLoaded = false

            val response5 = apiService.getUssdCodesAsync()
            if (response5.isSuccessful && response5.body()!!.isNotEmpty()) {
                mobiuzDao.deleteUssdCodes()
                response5.body()!!.forEach {
                    mobiuzDao.upsertUssdCodes(it)
                }
            } else isLoaded = false

            if (isLoaded) {
                unitProvider.saveLoadDate()
            }
        } catch (e: Exception) {
            isLoaded = false
        }
        return isLoaded
    }

}