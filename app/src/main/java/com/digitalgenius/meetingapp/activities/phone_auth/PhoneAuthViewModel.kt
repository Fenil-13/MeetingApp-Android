package com.digitalgenius.meetingapp.activities.phone_auth

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalgenius.meetingapp.utilities.Veriables
import com.digitalgenius.meetingapp.utilities.displayToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneAuthViewModel(val context: Context) : ViewModel() {
    val isOtpSend: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isVerificationDone: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var storedVerificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    init {
        isOtpSend.value=false
        isVerificationDone.value=false
    }

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                isOtpSend.postValue(true)
                isVerificationDone.postValue(true)
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                isOtpSend.postValue(true)
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                isOtpSend.postValue(true)
            }
        }

    fun send_otp(number: String) {
        val mNumber = "+91$number"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context as Activity)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(otp: String) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            storedVerificationId.toString(), otp
        )
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Veriables.firebaseUser=it.result?.user!!
                    isVerificationDone.postValue(true)
                } else {
                    (context as Activity).displayToast("Invalid OTP")
                }
            }.addOnFailureListener {
                (context as Activity).displayToast("Something Went wrong..")
            }
    }



}