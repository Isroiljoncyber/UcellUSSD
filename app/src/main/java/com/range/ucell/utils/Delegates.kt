package com.range.ucell.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import kotlinx.coroutines.*
import com.range.ucell.R
import java.io.IOException

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

fun ussdCall(code: String, context: Context) {
    val intent: Intent
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (isCallPermissionGranted(context)) {
            intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$code")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("com.android.phone.extra.slot", true)
            intent.putExtra("com.android.phone.extra.slot", true)
                context.startActivity(intent)
        } else {
            Toast.makeText(context, context.resources.getString(R.string.dialog_permission_settings), Toast.LENGTH_SHORT).show()
        }
    } else {
        intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$code")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}

fun isCallPermissionGranted(context: Context): Boolean {
    val request = "android.permission.CALL_PHONE"
    val result: Int = context.checkCallingOrSelfPermission(request)
    return result == PackageManager.PERMISSION_GRANTED
}

class NoConnectivityException: IOException()