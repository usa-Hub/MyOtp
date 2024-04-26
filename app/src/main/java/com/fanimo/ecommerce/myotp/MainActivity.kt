package com.fanimo.ecommerce.myotp

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fanimo.ecommerce.myotp.ui.theme.MyOtpTheme
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appSignatureHelper = AppSignatureHelper(this)
        val keys = appSignatureHelper.getAppSignatures()[0]
        Log.e("danisms", keys)


        SmsRetriever.getClient(this).apply {
            startSmsRetriever().apply {
                addOnSuccessListener {
                    Log.e("dani","addOnSuccessListener")

                    val smsBroadCastReceiver = SMSBroadCastReceiver()

                    val intentFilter = IntentFilter().apply {
                        addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        registerReceiver(smsBroadCastReceiver, intentFilter, RECEIVER_EXPORTED)
                        Log.e("dani", "RECEIVER_NOT_EXPORTED")
                    } else {
                        registerReceiver(smsBroadCastReceiver, intentFilter)
                        Log.e("dani","intentFilter")
                    }

                }
                addOnFailureListener{
                    Log.e("dani",it.message.toString())
                }
            }
        }

        setContent {
            MyOtpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(keys)
                }
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyOtpTheme {
        Greeting("Android")
    }
}