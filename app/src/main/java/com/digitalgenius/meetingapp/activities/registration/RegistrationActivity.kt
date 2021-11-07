package com.digitalgenius.meetingapp.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.digitalgenius.meetingapp.HomeActivity
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private var _binding:ActivityRegistrationBinding?=null
    private val binding get() = requireNotNull(_binding)

    private lateinit var mViewModel: RegistrationViewModel
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel=ViewModelProvider(this).get(RegistrationViewModel::class.java)

        setListener()
    }

    private fun setListener() {
        binding.btnSignUp.setOnClickListener {
            if(checkFrom()){
                mViewModel.createOrUpdateUser()
                goToHomeScreen()
            }
        }
    }

    private fun goToHomeScreen() {
        startActivity(Intent(this@RegistrationActivity,HomeActivity::class.java))
        finish()
    }

    private fun checkFrom(): Boolean {
        if (binding.etEmail.text.toString().isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Enter Email Address", Toast.LENGTH_SHORT).show()
            return false
        } else if (!binding.etEmail.text.toString().matches(Regex(emailPattern))) {
            Toast.makeText(this@RegistrationActivity, "Enter Valid Email Address", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (binding.etUsername.text.toString().isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Enter Username", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}