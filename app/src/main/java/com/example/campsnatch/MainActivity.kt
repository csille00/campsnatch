package com.example.campsnatch

import ServerProxy
import android.os.Bundle
import com.example.campsnatch.databinding.ActivityMainBinding
import com.example.campsnatch.support.CampsnatchActivity

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
    }
}