package com.kmitl.mythesis.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class HPEditText(context: Context, attrs: AttributeSet):AppCompatEditText(context, attrs) {
    init {
        // Call the func to apply font to component
        applyFont()
    }

    private fun applyFont() {
        // This is used to get the file from the assets folder and set it to the title textView
        val typeface: Typeface =
                Typeface.createFromAsset(context.assets, "Sarabun-Light.ttf")
        setTypeface(typeface)
    }
}