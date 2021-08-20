package com.kmitl.mythesis.models

import android.os.Parcelable
import com.google.type.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val id: String ="",
    val userName: String ="",
    val email: String ="",
    val image: String ="",
    val birthDay: String="",
    val gender: String = "",
    val residentType: String ="",
    val mobile: Long = 0,
    val profileCompleted: Int = 0): Parcelable