package com.range.ucell.utils

import android.net.Uri

/**
 * Created by Javoh on 16.08.2020.
 */

class UssdCodes {

    companion object {
        val encodedHash: String = Uri.encode("#")
        val dealerCode: String = "*010100180$encodedHash"
        const val dealerCodeHash: String = "*010100180#"

        val balanceUssdRu = "*100*1$encodedHash"
        val balanceUssdUz = "*100*1$encodedHash"
        val lastPayment = "*200$encodedHash"
        val myDisCharge = "*171*1*3$encodedHash"
        val myNumber = "*450$encodedHash"
        val allMyNumbers = "*360$encodedHash"
        val RemainTrafBtn = "*107$encodedHash"
        val CheckActServ = "*401$encodedHash"

        val getSettings = "*111*021$encodedHash"
        val turnOnMobileNet = "*111*0011$encodedHash"
        val turnOffMobileNet = "*111*0010$encodedHash"
        val turnOffOnNet = "*202*0$encodedHash"

        val packageMonth = "*107$encodedHash"
        val packageTASIX = "*616$encodedHash"
        val packageInfinity = "*555*4*10*1$encodedHash"

        val smsCheckDay = "148*3$encodedHash"
        val smsCheckMonth = "147$encodedHash"

        val minuteCheck = "*109$encodedHash"

//        const val netPackets: String = "*171*"

        val holdCallCancel = "#43$encodedHash"
        val missedCallCancel = "*111*0130$encodedHash"
        val antiAONCancel = "*111*0100$encodedHash"
        val lte4GCancel = "*222*0$encodedHash"
    }
}