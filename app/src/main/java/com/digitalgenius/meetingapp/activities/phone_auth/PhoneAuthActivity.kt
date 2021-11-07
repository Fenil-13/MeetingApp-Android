package com.digitalgenius.meetingapp.activities.phone_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.digitalgenius.meetingapp.HomeActivity
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.databinding.ActivityPhoneAuthBinding
import com.digitalgenius.meetingapp.utilities.*

class PhoneAuthActivity : AppCompatActivity() {
    var _binding: ActivityPhoneAuthBinding? = null
    val binding get() = requireNotNull(_binding)
    private lateinit var mViewModel: PhoneAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this, PhoneAuthViewModelFactory(this@PhoneAuthActivity))
            .get(PhoneAuthViewModel::class.java)

        initUi()
        setListener()
    }

    private fun setListener() {

        mViewModel.isOtpSend.observe(this, {
            if (it) {
                Functions.dismissProgressDialog()
                binding.btnNext.text = "Verify Otp"
                binding.layoutOtp.show()
            } else {
                Functions.dismissProgressDialog()
                binding.layoutOtp.hide()
            }
        })

        mViewModel.isVerificationDone.observe(this, {
            if (it) {
                saveLoginDetails()
                displayToast("Verification Successfully")
                startActivity(Intent(this@PhoneAuthActivity, HomeActivity::class.java))
                finish()
            }
        })


        binding.btnNext.setOnClickListener {
            if (binding.btnNext.text.toString() == getString(R.string.send_otp)) {
                if (isValidPhoneNumber()) {
                    Functions.showProgressDialog(this@PhoneAuthActivity,"Loading")
                    mViewModel.send_otp(binding.etPhoneNumber.text.toString())
                }
            } else {
                if (isValidOtp()) {
                    mViewModel.verifyOtp(binding.etOtp.text.toString())
                }

            }
        }
    }

    private fun initUi() {
        if (mViewModel.isOtpSend.value!!) {
            binding.layoutOtp.show()
        } else {
            binding.layoutOtp.hide()
        }
    }

    private fun isValidPhoneNumber(): Boolean {
        if (binding.etPhoneNumber.text.toString().length == 10) {
            return true
        }
        this@PhoneAuthActivity.displayToast("Enter 10 digit Number")
        return false
    }

    private fun isValidOtp(): Boolean {
        if (binding.etOtp.text.toString().length == 10) {
            return true
        }
        this@PhoneAuthActivity.displayToast("Enter 6 digit otp")
        return false
    }

    private fun saveLoginDetails() {
        val sharedPrefManager: SharedPrefManager =
            SharedPrefManager.getInstance(applicationContext)
        sharedPrefManager.setStringData("Login", "True")
    }
}