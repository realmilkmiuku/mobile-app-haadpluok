package com.kmitl.mythesis.view.adapters

import android.content.Context
import com.kmitl.mythesis.models.Vegetable

interface CommunicatorVegan {
    fun passData( context: Context, list: ArrayList<Vegetable>, fragment: String)
}