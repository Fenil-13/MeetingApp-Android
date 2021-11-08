package com.digitalgenius.meetingapp.fragments.report

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.databinding.ReportFragmentBinding

class ReportFragment : Fragment() {


    private lateinit var viewModel: ReportViewModel
    private lateinit var binding:ReportFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= ReportFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
    }
}