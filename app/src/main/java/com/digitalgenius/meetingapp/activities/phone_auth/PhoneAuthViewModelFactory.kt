package com.digitalgenius.meetingapp.activities.phone_auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PhoneAuthViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhoneAuthViewModel(context) as T
    }
}