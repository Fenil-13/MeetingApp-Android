package com.digitalgenius.meetingapp.fragments.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.activities.meeting.MeetingActivity
import com.digitalgenius.meetingapp.adapter.MeetingAdapter
import com.digitalgenius.meetingapp.databinding.HomeFragmentBinding
import com.digitalgenius.meetingapp.fragments.DialogMeetingFragment
import com.digitalgenius.meetingapp.interfaces.MeetingClickListener
import com.digitalgenius.meetingapp.interfaces.MeetingDialogListener

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var meetingAdapter: MeetingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= HomeFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setUpViews()
        setListener()
    }

    private fun setListener() {
        binding.ivAddMeeting.setOnClickListener {
            startActivity(Intent(requireContext(),MeetingActivity::class.java))
        }
    }

    private fun setUpViews() {
        meetingAdapter= MeetingAdapter(requireContext(),object : MeetingClickListener{
            override fun onClick(position: Int) {
                openSuccessDialog()
            }
        })

        binding.rvMyMeeting.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter=meetingAdapter
        }
    }
    private fun openSuccessDialog() {
        val fragmentManager: FragmentManager = childFragmentManager
        val newFragment = DialogMeetingFragment(object : MeetingDialogListener {
            override fun onClick() {

            }

        }, "Meeting Details")
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.fragment_container_view, newFragment).addToBackStack(null).commit()
    }
}