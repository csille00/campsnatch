package com.example.campsnatch.model

import com.google.gson.annotations.SerializedName

data class GeoJson(
    @SerializedName("COORDINATES") val coordinates: List<Double>,
    @SerializedName("TYPE") val type: String
)