package com.kmitl.mythesis.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plant (
    val user_id: String ="",
    val user_name: String ="",
    val plantName: String ="",
    val plantType: String="",
    val plantBday: String ="",
    val plantDes: String = "",
    val image: String ="",
    var plant_id: String = "",
): Parcelable

