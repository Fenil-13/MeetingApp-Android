package com.digitalgenius.meetingapp.utilities

import android.app.Activity
import android.view.View
import android.widget.Toast

fun View.show(){
    this.visibility=View.VISIBLE
}

fun View.hide(){
    this.visibility=View.GONE
}

fun Activity.displayToast(message:String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}