package com.kmitl.mythesis.models

import android.os.Parcelable
import com.google.type.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
class AddPlant (
    val id: String ="",
    val plantName: String ="",
    val image: String ="",
    val PlantType: String="",
    val plantBday: String ="",
    val plantDes: String = "", ): Parcelable

