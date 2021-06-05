package com.kmitl.mythesis.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kmitl.mythesis.utils.Constants

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Home"
    }
    val text: LiveData<String> = _text
}
