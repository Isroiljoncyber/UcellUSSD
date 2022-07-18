package com.range.ucell.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface TwilloInterface {

    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
    suspend fun sendMessage(
        @Path("ACCOUNT_SID") accountSId: String?,
        @Header("Authorization") signature: String?,
        @FieldMap smsdata: Map<String, String>
    ): Response<ResponseBody>

}