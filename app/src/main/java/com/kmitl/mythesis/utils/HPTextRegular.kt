package com.kmitl.mythesis.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

class HPTextRegular(context: Context, attrs: AttributeSet): AppCompatTextView (context, attrs) {
    init {
        // Call the func to apply font to component
        applyFont()
    }

    private fun applyFont() {
        // This is used to get the file from the assets folder and set it to the title textView
        val typeface: Typeface =
                Typeface.createFromAsset(context.assets, "Sarabun-Regular.ttf")
        setTypeface(typeface)
    }
}