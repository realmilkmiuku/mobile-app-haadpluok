package com.kmitl.mythesis.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Goods (
    val goods_name: String ="",
    val goods_price: String ="",
    val goods_details: String ="",
    val goods_link: String ="",
    val image: String ="",
    var goods_id: String = "",
): Parcelable