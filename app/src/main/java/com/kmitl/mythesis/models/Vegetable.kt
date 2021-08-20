package com.kmitl.mythesis.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vegetable (
    val ve_name: String ="",
    val ve_locate: String ="",
    val ve_detail_how: String ="",
    val ve_details_water_sun: String ="",
    val ve_size: String ="",
    val ve_crop: String ="",
    val ve_time_crop: String ="",
    val image: String ="",
    var ve_id: String = "",
): Parcelable
