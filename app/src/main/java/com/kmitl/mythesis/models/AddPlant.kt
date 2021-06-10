package com.kmitl.mythesis.models

import android.os.Parcelable
import com.google.type.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
class AddPlant (
    val id: String ="",
    val plantName: String ="",
    val plantImage: String ="",
    val plantType: String="",
    val plantBday: String ="",
    val plantDes: String = "", ): Parcelable

