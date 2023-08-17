package com.example.campsnatch.model

data class CampgroundResponse (
    val success: Boolean,
    val campgrounds: MutableList<Campground>
)