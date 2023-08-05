package com.example.campsnatch.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Campground(
    @SerializedName("campground_id") val campgroundId: String,
    @SerializedName("campground_name") val campgroundName: String,
    @SerializedName("geo_json") val geoJson: String
) {
    fun getGeoJsonModel(): GeoJson {
        return Gson().fromJson(geoJson, GeoJson::class.java)
    }
}