package com.digitalgenius.meetingapp.fragments.report

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.databinding.ReportFragmentBinding
import com.digitalgenius.meetingapp.utilities.Functions
import com.digitalgenius.meetingapp.utilities.Veriables
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

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
        setListener()
    }

    private fun setListener() {
        viewModel.dataRetrived.observe(this.viewLifecycleOwner){
            when(it){
                "Success"->{
                    setUpUI()
                }
                "Not Found"->{
                    setUpDefaultUI()
                }
                "Error"->{
                    Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpDefaultUI() {
        binding.tvDailyMeetingCount.text="0"
        binding.tvDailyMeetingHour.text="0 Hours"
    }

    private fun setUpUI() {
        binding.tvDailyMeetingCount.text=viewModel.data.size.toString()
        var hour:Long=0L
        var min:Long=0L
        var sec:Long=0L

        for(item in viewModel.data){
            val result= calculateDiffTime(item.starttime,item.endtime)
            hour+=result[0]
            min+=result[1]
            sec+=result[2]
        }

        if(sec>=60){
            min+=sec/60
        }

        if(min>=60){
            hour+=min/60
        }

        binding.tvDailyMeetingHour.text="$hour Hour $min Min $sec Sec"

    }

    private fun calculateDiffTime(time1:String,time2:String): ArrayList<Long> {
        val result=ArrayList<Long>()
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val date1: Date = simpleDateFormat.parse(time1)
        val date2: Date = simpleDateFormat.parse(time2)

        val differenceInMilliSeconds = abs(date2.time - date1.time)

        val differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)
                % 24)
        result.add(differenceInHours)
        val differenceInMinutes = differenceInMilliSeconds / (60 * 1000) % 60
        result.add(differenceInMinutes)

        val differenceInSeconds = differenceInMilliSeconds / 1000 % 60
        result.add(differenceInSeconds)

        // Printing the answer
        Log.d(Veriables.TAG,
            "Difference is " + differenceInHours + " hours "
                    + differenceInMinutes + " minutes "
                    + differenceInSeconds + " Seconds. ");

        return result
    }

    override fun onResume() {
        super.onResume()

        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)
        binding.tvDay.text=dayOfTheWeek


        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = df.format(c)


        viewModel.get_daily_data(formattedDate.toString())
    }

    override fun onPause() {
        super.onPause()
        Functions.dismissProgressDialog()
    }
}