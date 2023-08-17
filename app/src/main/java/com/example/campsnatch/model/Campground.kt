package com.example.campsnatch.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campground(
    @SerializedName("campground_id") val campgroundId: String,
    @SerializedName("campground_name") val campgroundName: String,
    @SerializedName("geo_json") val geoJson: String
) : Parcelable {
    fun getGeoJsonModel(): GeoJson {
        return Gson().fromJson(geoJson, GeoJson::class.java)
    }
}