package com.example.campsnatch.model

import java.util.*

data class CampgroundAvailabilitiesResponse (
    var success: Boolean,
    var availabilities: Map<Date, Boolean>
)