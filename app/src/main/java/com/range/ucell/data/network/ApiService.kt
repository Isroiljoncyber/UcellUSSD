package com.range.ucell.data.network

import com.range.ucell.data.db.entity.*
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("packets.json")
    suspend fun getPacketsAsync(): Response<PacketsResponse>

    @GET("tariffs.json")
    suspend fun getRateAsync(): Response<RateResponse>

    @GET("minutes.json")
    suspend fun getMinutesAsync(): Response<MinutesResponse>

    @GET("services.json")
    suspend fun getServicesAsync(): Response<ServiceResponse>

    @GET("ussd_codes.json")
    suspend fun getUssdCodesAsync(): Response<UssdCodeResponse>

    @GET("sale.json")
    suspend fun getSaleAsync(): Response<SaleModel>

    @GET("dealerCode.json")
    suspend fun getDealerCode(): Response<DealerCode>

    @GET("version.json")
    suspend fun getVersion(): Response<Version>

    @GET("banner.json")
    suspend fun getBanners(): Response<BannerResponse>

//    companion object {
//        operator fun invoke(): ApiService {
//
//            val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//
//            val okHttpClient = OkHttpClient.Builder()
//                    .addInterceptor(logging)
//                    .build()
//
//            return Retrofit.Builder()
//                    .client(okHttpClient)
//                    .baseUrl("https://raw.githubusercontent.com/Javoh29/Mobiuz/master/")
//                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(ApiService::class.java)
//        }
//    }
}