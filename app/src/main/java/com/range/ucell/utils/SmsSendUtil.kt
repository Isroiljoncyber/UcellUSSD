package com.range.ucell.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.telephony.SmsManager
import android.widget.Toast
import com.range.ucell.R


class SmsSendUtil(val context: Context) {

    fun sendSmsRequest(smsBody: String, dialogTitle: String?, centre_number: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (isSMSPermissionGranted(context)) {
//                val builder = AlertDialog.Builder(context)
//                builder.setTitle(title)
//                    .setMessage("Do you want to but this package ?")
//                    .setNegativeButton("No") { d,ialogInterface, _ -> dialogInterface.dismiss() }
//                    .setPositiveButton("Yes") { dialogInterface, _ ->
                sendSms(smsBody, centre_number)
//                        dialogInterface.dismiss()
//                builder.create().show()
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.dialog_permission_settings),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(context, "Error with phone version !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun sendSms(number: String, centre_number: String) {
        val handler = Handler()
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        handler.postDelayed({
            progressDialog.dismiss()
            val smsManager = SmsManager.getDefault()
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
                .setPositiveButton(
                    "OK"
                ) { dialogInterface, i -> dialogInterface.dismiss() }
            try {
                smsManager.sendTextMessage(centre_number, null, number, null, null)
                builder.setMessage(context.resources.getString(R.string.text_dialog_progres_response))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                builder.setMessage("Error occurred")
            }
            builder.create().show()
        }, 1500)
    }

    fun isSMSPermissionGranted(context: Context): Boolean {
        val request = "android.permission.SEND_SMS"
        val result: Int = context.checkCallingOrSelfPermission(request)
        return result == PackageManager.PERMISSION_GRANTED
    }

//    fun sendFun() {
//        val smsUri = Uri.parse("smsto:$centre_number")
//        val intent = Intent(Intent.ACTION_VIEW, smsUri)
//        intent.putExtra("sms_body", smsBody)
//        intent.type = "vnd.android-dir/mms-sms"
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        context.startActivity(intent)
//    }
}