package com.digitalgenius.meetingapp.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.digitalgenius.meetingapp.activities.home.HomeActivity
import com.digitalgenius.meetingapp.databinding.ActivityRegistrationBinding
import com.digitalgenius.meetingapp.utilities.Functions
import com.digitalgenius.meetingapp.utilities.SharedPrefManager
import com.digitalgenius.meetingapp.utilities.Veriables

class RegistrationActivity : AppCompatActivity() {
    private var _binding: ActivityRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var mViewModel: RegistrationViewModel
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        setListener()
    }

    private fun setListener() {
        binding.btnSignUp.setOnClickListener {
            if (checkFrom()) {
                mViewModel.call_create_user(
                    intent.getStringExtra("authId").toString(),
                    binding.etEmail.text.toString(),
                    binding.etUsername.text.toString()
                )
            }
        }
        mViewModel.userCreated.observe(this) {
            if (it) {
                saveDataInPref()
                goToHomeScreen()
            }
        }


    }

    private fun saveDataInPref() {
        val sharedPrefManager: SharedPrefManager =
            SharedPrefManager.getInstance(applicationContext)
        sharedPrefManager.setStringData("Login", "True")
        sharedPrefManager.setStringData("userEmail", Veriables.addUserResponse?.userEmail)
        sharedPrefManager.setStringData("userAuthid", Veriables.addUserResponse?.userAuthid)
        sharedPrefManager.setStringData("userName", Veriables.addUserResponse?.userName)
        sharedPrefManager.setStringData("id", "" + mViewModel.addUserResponse.id)
    }


    private fun goToHomeScreen() {
        startActivity(Intent(this@RegistrationActivity, HomeActivity::class.java))
        finish()
    }

    private fun checkFrom(): Boolean {
        if (binding.etEmail.text.toString().isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Enter Email Address", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (!binding.etEmail.text.toString().matches(Regex(emailPattern))) {
            Toast.makeText(
                this@RegistrationActivity,
                "Enter Valid Email Address",
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        } else if (binding.etUsername.text.toString().isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Enter Username", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}