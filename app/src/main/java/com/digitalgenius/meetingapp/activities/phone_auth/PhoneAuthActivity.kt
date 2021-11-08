package com.digitalgenius.meetingapp.activities.phone_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.activities.home.HomeActivity
import com.digitalgenius.meetingapp.activities.registration.RegistrationActivity
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
                Functions.showProgressDialog(this@PhoneAuthActivity, "Loading")
                mViewModel.find_user(mViewModel.auth.uid!!)
            }
        })

        mViewModel.isUserAvailable.observe(this) {
            if (it) {
                Functions.dismissProgressDialog()
                saveDataInPref()
                goToHomeScreen()
            } else {
                if(Functions.pDialog!=null){
                    Functions.dismissProgressDialog()
                    goToRegistrationActivity()
                }

            }
        }

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.text.toString() == getString(R.string.send_otp)) {
                if (isValidPhoneNumber()) {
                    Functions.showProgressDialog(this@PhoneAuthActivity, "Loading")
                    mViewModel.send_otp(binding.etPhoneNumber.text.toString())
                }
            } else {
                if (isValidOtp()) {
                    mViewModel.verifyOtp(binding.etOtp.text.toString())
                }

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
        startActivity(Intent(this@PhoneAuthActivity, HomeActivity::class.java))
        finish()
    }

    private fun goToRegistrationActivity() {
        val nextIntent = Intent(this@PhoneAuthActivity, RegistrationActivity::class.java)
        nextIntent.putExtra("authId", mViewModel.auth.currentUser?.uid)
        startActivity(nextIntent)
        finish()
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
        if (binding.etOtp.text.toString().length == 6) {
            return true
        }
        this@PhoneAuthActivity.displayToast("Enter 6 digit otp")
        return false
    }

    private fun saveLoginDetails() {
        val sharedPrefManager: SharedPrefManager =
            SharedPrefManager.getInstance(applicationContext)
        sharedPrefManager.setStringData("Login", "True")
        sharedPrefManager.setStringData(
            "phoneNumber",
            "+91 ${binding.etPhoneNumber.text.toString()}"
        )
    }
}