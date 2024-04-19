package com.yashvant.revander.niche_android_concepts.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun BroadcastExample() {
    var broadcastMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Define a broadcast receiver
    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                // Extract data from the broadcast intent
                val message = it.getStringExtra("message")
                broadcastMessage = message ?: ""
            }
        }
    }

    // Register the broadcast receiver
    DisposableEffect(Unit) {
        val filter = IntentFilter("com.example.broadcast.ACTION")
        context.registerReceiver(broadcastReceiver, filter)

        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    Column {
        Text(text = "Broadcast Message: $broadcastMessage")

        Button(
            onClick = {
                // Broadcast an intent
                val intent = Intent("com.example.broadcast.ACTION").apply {
                    putExtra("message", "Hello from Jetpack Compose!")
                }
                context.sendBroadcast(intent)
            }
        ) {
            Text(text = "Send Broadcast")
        }

        // Handle back press
        BackHandler {
            // Unregister receiver on back press
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}
