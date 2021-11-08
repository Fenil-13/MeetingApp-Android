package com.digitalgenius.meetingapp.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.activities.meeting.MeetingActivity
import com.digitalgenius.meetingapp.adapter.MeetingAdapter
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.databinding.HomeFragmentBinding
import com.digitalgenius.meetingapp.fragments.DialogMeetingFragment
import com.digitalgenius.meetingapp.interfaces.MeetingClickListener
import com.digitalgenius.meetingapp.interfaces.MeetingDialogListener
import com.digitalgenius.meetingapp.utilities.Functions
import com.digitalgenius.meetingapp.utilities.Veriables
import com.digitalgenius.meetingapp.utilities.hide
import com.digitalgenius.meetingapp.utilities.show

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
            val meetingActivity=Intent(requireContext(),MeetingActivity::class.java)
            meetingActivity.putExtra("type","new")
            startActivity(meetingActivity)
        }

        viewModel.dataFetched.observe(this.viewLifecycleOwner){
            when(it){
                "Get_data"->{
                    Functions.dismissProgressDialog()
                    meetingAdapter= MeetingAdapter(requireContext(),viewModel.data,object :MeetingClickListener{
                        override fun onClick(position: Int) {
                            openSuccessDialog(viewModel.data[position])
                        }

                        override fun onDelete(position: Int) {
                            Functions.showProgressDialog(requireContext(),"Deleting..")
                            viewModel.deleteMeeting(viewModel.data[position])
                        }

                        override fun onUpdate(position: Int) {
                            val meetingActivity=Intent(requireContext(),MeetingActivity::class.java)
                            meetingActivity.putExtra("type","edit")
                            Veriables.currentMeeting=viewModel.data[position]
                            startActivity(meetingActivity)
                        }

                        override fun onShare(position: Int) {
                            shareMeeting(position)
                        }
                    })
                    binding.rvMyMeeting.apply {
                        layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                        setHasFixedSize(true)
                        adapter=meetingAdapter
                    }
                    meetingAdapter.notifyDataSetChanged()
                    if (viewModel.data.size==0){
                        binding.tvNoMeeting.show()
                    }else{
                        binding.tvNoMeeting.hide()
                    }
                }
                "Error"->{
                    Functions.dismissProgressDialog()
                    if (viewModel.data.size==0){
                        binding.tvNoMeeting.show()
                    }else{
                        binding.tvNoMeeting.hide()
                    }
                    Toast.makeText(requireContext(), "Something went wrong...", Toast.LENGTH_SHORT).show()
                }
                "delete"->{
                    Functions.dismissProgressDialog()
                    onResume()
                }
            }
        }
    }

    private fun shareMeeting(position: Int) {
        val data = viewModel.data[position]
        val shareText =
            "Hello Friends,\n I schedule Meeting about ${data.meetingtitle} at ${data.starttime} to ${data.endtime}.I heartly invite to you.\n\n" +
                    "Join through below link\n" +
                    "${data.meetinglink}\n\n" +
                    "Meet you in meeting.Thanks for Support."

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(intent, "Share Meeting Details"));
    }

    private fun setUpViews() {
        meetingAdapter= MeetingAdapter(requireContext(),viewModel.data,object :MeetingClickListener{
            override fun onClick(position: Int) {
                openSuccessDialog(viewModel.data[position])
            }
            override fun onDelete(position: Int) {
                Functions.showProgressDialog(requireContext(),"Deleting..")
                viewModel.deleteMeeting(viewModel.data[position])
            }

            override fun onUpdate(position: Int) {
                val meetingActivity=Intent(requireContext(),MeetingActivity::class.java)
                meetingActivity.putExtra("type","edit")
                Veriables.currentMeeting=viewModel.data[position]
                startActivity(meetingActivity)
            }

            override fun onShare(position: Int) {
                shareMeeting(position)
            }
        })

        binding.rvMyMeeting.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter=meetingAdapter
        }
    }
    private fun openSuccessDialog(meetingData: CreateMeetingResponse) {
        val fragmentManager: FragmentManager = childFragmentManager
        val newFragment = DialogMeetingFragment(object : MeetingDialogListener {
            override fun onClick() {

            }

        }, meetingData)
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(R.id.fragment_container_view, newFragment).addToBackStack(null).commit()
    }

    override fun onResume() {
        super.onResume()
        val addUserResponse =Veriables.addUserResponse
        Functions.showProgressDialog(requireContext(),"Getting Meeting...")
        viewModel.getAllMeetings(addUserResponse)
    }

    override fun onPause() {
        super.onPause()
        Functions.dismissProgressDialog()
    }
}