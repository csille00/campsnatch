package com.example.campsnatch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.campsnatch.databinding.FragMainBinding
import com.example.campsnatch.support.CampsnatchActivity
import com.example.campsnatch.support.viewBinding
import com.google.firebase.messaging.FirebaseMessaging

class FragmentMain : Fragment() {
    private val binding: FragMainBinding? by viewBinding(FragMainBinding::bind)
    private var cont: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cont = activity?.applicationContext
        return FragMainBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: MainActivity = requireActivity() as MainActivity

        binding?.plusIcon?.setOnClickListener {
            activity.replaceFragment(FragmentSearchCampground(), R.id.frag_container)
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