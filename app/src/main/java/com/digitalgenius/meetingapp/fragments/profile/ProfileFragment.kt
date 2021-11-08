package com.digitalgenius.meetingapp.fragments.profile

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.databinding.ProfileFragmentBinding
import android.content.Intent
import android.net.Uri
import com.digitalgenius.meetingapp.activities.splash.SplashActivity
import com.digitalgenius.meetingapp.utilities.SharedPrefManager
import com.digitalgenius.meetingapp.utilities.Veriables


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        initViews()
        setListener()
    }

    private fun initViews() {
        binding.tvUsername.text=Veriables.addUserResponse?.userName
        binding.tvUserEmail.text=Veriables.addUserResponse?.userEmail
        binding.tvUserPhoneNumber.text=SharedPrefManager
            .getInstance(requireContext().applicationContext)
            .getStringData("phoneNumber")
    }

    private fun setListener() {
        binding.layoutGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_link))))
        }

        binding.layoutPolicy.setOnClickListener {
            startActivity( Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy))))
        }

        binding.layoutTerm.setOnClickListener {
            startActivity( Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.term_and_condition))))
        }

        binding.layoutLogout.setOnClickListener {
            val sharedPrefManager: SharedPrefManager = SharedPrefManager.getInstance(requireContext().applicationContext)
            sharedPrefManager.setStringData("Login", "False")
            startActivity(Intent(requireContext(),SplashActivity::class.java))
            (context as Activity ).finish()
        }
    }

}