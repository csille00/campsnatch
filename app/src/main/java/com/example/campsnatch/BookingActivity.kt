package com.example.campsnatch

import ServerProxy
import android.os.Bundle
import android.widget.Toast
import com.example.campsnatch.databinding.ActivityBookingBinding
import com.example.campsnatch.model.Campground
import com.example.campsnatch.model.CampgroundAvailabilityRequest
import com.example.campsnatch.support.CampsnatchActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class BookingActivity : CampsnatchActivity() {
    private lateinit var binding: ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the passed Campground object
        val campObject: Campground? = intent.getParcelableExtra("CAMP_OBJECT")
        campObject?.let {
            binding.tvTitle.text = campObject.campgroundName

            val request = CampgroundAvailabilityRequest(getFirstDayOfCurrentMonth(), campObject?.campgroundId)

            val serverProxy = ServerProxy()

            serverProxy.getCampgroundAvailabilities(request) { response ->
                Toast.makeText(this, "response", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getFirstDayOfCurrentMonth(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar.time)
    }
}