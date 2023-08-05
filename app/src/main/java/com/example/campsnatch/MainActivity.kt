package com.example.campsnatch

import ServerProxy
import android.os.Bundle
import com.example.campsnatch.databinding.ActivityMainBinding
import com.example.campsnatch.support.CampsnatchActivity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : CampsnatchActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plusIcon.setOnClickListener{
            val serverProxy = ServerProxy()
            serverProxy.getCampgrounds { response ->
                // Handle the response here
            }
        }

        // Request FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Here, you have the FCM registration token
                // You can now send this token to your server to identify the device
                // and send push notifications to it.

                // For testing purposes, you can log the token to the console
                token?.let {
                    println("FCM Registration Token: $it")
                }
            } else {
                // Failed to get the FCM token
                println("Failed to get FCM registration token: ${task.exception}")
            }
        }
    }


}