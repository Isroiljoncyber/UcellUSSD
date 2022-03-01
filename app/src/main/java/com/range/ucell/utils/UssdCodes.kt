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

        const val callCentre = "+998971300909"
        val balanceUssdRu = "*100*1$encodedHash"
        val balanceUssdUz = "*100*1$encodedHash"
        val lastPayment = "*171*1*2$encodedHash"
        val myDisCharge = "*171*1*3$encodedHash"
        val myNumber = "*150$encodedHash"
        val allMyNumbers = "*151$encodedHash"
        val RemainTrafBtn = "*102$encodedHash"
        val CheckActServ = "*140$encodedHash"
        val languageChangeRu = "*111*0*2$encodedHash"
        val languageChangeUz = "*111*0*1$encodedHash"

        val getSettings = "*111*021$encodedHash"
        val turnOnMobileNet = "*111*0011$encodedHash"
        val turnOffMobileNet = "*111*0010$encodedHash"
        val turnOffOnNet = "*202*0$encodedHash"

        val packageCheck = "*171*019$encodedHash"
        val nightCheck = "*203$encodedHash"
        val miniCheck = "*102$encodedHash"
        const val netPackets: String = "*171*"

        val minuteCheck = "*103$encodedHash"

        val smsCheck = "*111*018$encodedHash"
        val sms100 = "*111*018*1$encodedHash"
        val sms300 = "*111*018*2$encodedHash"

        val holdCallCancel = "#43$encodedHash"
        val missedCallCancel = "*111*0130$encodedHash"
        val antiAONCancel = "*111*0100$encodedHash"
        val lte4GCancel = "*222*0$encodedHash"
    }
}