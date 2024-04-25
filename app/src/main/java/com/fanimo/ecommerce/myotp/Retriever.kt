package com.fanimo.ecommerce.myotp

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever

@Composable
fun SmsRetriever()
{
    val context = LocalContext.current
    SmsRetriever.getClient(context).apply {
        startSmsRetriever().apply {
            addOnSuccessListener {
                Log.e("dani","addOnSuccessListener")

                val smsBroadCastReceiver = SMSBroadCastReceiver()

                val intentFilter = IntentFilter().apply {
                    addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    registerReceiver(context, smsBroadCastReceiver, intentFilter, ContextCompat.RECEIVER_EXPORTED)
                    Log.e("dani", "RECEIVER_NOT_EXPORTED")
//                } else {
//                    registerReceiver(context, smsBroadCastReceiver, intentFilter, 5)
//                    Log.e("dani","intentFilter")
//                }

            }
            addOnFailureListener{
                Log.e("dani",it.message.toString())
            }
        }
    }
}