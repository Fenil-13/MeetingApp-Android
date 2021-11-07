package com.digitalgenius.meetingapp.utilities

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog

class Functions {

    companion object{
        var pDialog:SweetAlertDialog?=null
        fun showProgressDialog(context: Context,displayMessage:String){
            pDialog= SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE)
            pDialog?.apply {
                progressHelper.barColor= Color.parseColor("#1566E0")
                titleText = displayMessage
                setCancelable(false)
                show()
            }
        }
        fun dismissProgressDialog(){
            pDialog?.apply {
                dismissWithAnimation()
            }
            pDialog=null
        }
    }
}