package com.digitalgenius.meetingapp.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.digitalgenius.meetingapp.activities.home.HomeActivity
import com.digitalgenius.meetingapp.activities.phone_auth.PhoneAuthActivity
import com.digitalgenius.meetingapp.api.responses.AddUserResponse
import com.digitalgenius.meetingapp.databinding.ActivitySplashBinding
import com.digitalgenius.meetingapp.utilities.SharedPrefManager
import com.digitalgenius.meetingapp.utilities.Veriables

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
                    setProfileData()
                    goToHomeActivity()
                } else {
                    goToAuthActivity()
                }
            }, 2000
        )
    }

    private fun setProfileData() {
        val sharedPrefManager: SharedPrefManager =
            SharedPrefManager.getInstance(applicationContext)
        Veriables.addUserResponse = AddUserResponse(
            (sharedPrefManager.getStringData("id")).toInt(),
            sharedPrefManager.getStringData("userAuthid"),
            sharedPrefManager.getStringData("userEmail"),
            sharedPrefManager.getStringData("userName")
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