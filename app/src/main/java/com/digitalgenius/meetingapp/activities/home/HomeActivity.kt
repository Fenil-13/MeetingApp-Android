package com.digitalgenius.meetingapp.activities.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mViewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
        NavigationUI.setupWithNavController(binding.navView, navController)

        setUpMeetings()
    }

    private fun setUpMeetings() {

    }
}