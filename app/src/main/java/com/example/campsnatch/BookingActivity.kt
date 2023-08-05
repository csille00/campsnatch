package com.example.campsnatch

import ServerProxy
import android.os.Bundle
import android.widget.Toast
import com.example.campsnatch.databinding.ActivityBookingBinding
import com.example.campsnatch.model.CampgroundAvailabilityRequest
import com.example.campsnatch.support.CampsnatchActivity

class BookingActivity:CampsnatchActivity() {
    private lateinit var binding: ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val request = CampgroundAvailabilityRequest("2023-08-01", "234059")

        val serverProxy = ServerProxy()

        serverProxy.getCampgroundAvailabilities(request) { response ->
            Toast.makeText(this, "response", Toast.LENGTH_SHORT).show()

        }
    }
}