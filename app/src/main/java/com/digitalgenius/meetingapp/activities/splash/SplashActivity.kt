package com.digitalgenius.meetingapp.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.digitalgenius.meetingapp.HomeActivity
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.activities.phone_auth.PhoneAuthActivity
import com.digitalgenius.meetingapp.databinding.ActivityPhoneAuthBinding
import com.digitalgenius.meetingapp.databinding.ActivitySplashBinding
import com.digitalgenius.meetingapp.utilities.SharedPrefManager

class SplashActivity : AppCompatActivity() {
    var _binding: ActivitySplashBinding? = null
    val binding get() = requireNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler(mainLooper).postDelayed(
            {
                val sharedPrefManager =
                    SharedPrefManager.getInstance(applicationContext)
                if (sharedPrefManager.getStringData("Login").equals("True")) {
                    goToHomeActivity()
                } else {
                    goToAuthActivity()
                }
            }, 2000
        )
    }

    private fun goToAuthActivity() {
        startActivity(Intent(this@SplashActivity, PhoneAuthActivity::class.java))
        finish()
    }

    private fun goToHomeActivity() {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }
}