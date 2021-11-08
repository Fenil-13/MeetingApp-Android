package com.digitalgenius.meetingapp.activities.meeting

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.api.requests.CreateMeetingRequest
import com.digitalgenius.meetingapp.api.requests.UpdateMeetingRequest
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.databinding.ActivityMeetingBinding
import com.digitalgenius.meetingapp.utilities.Functions
import com.digitalgenius.meetingapp.utilities.SharedPrefManager
import com.digitalgenius.meetingapp.utilities.Veriables
import com.digitalgenius.meetingapp.utilities.displayToast
import java.text.DateFormat
import java.util.*
import kotlin.math.log

class MeetingActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityMeetingBinding
    private lateinit var meetingViewModel: MeetingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        meetingViewModel = ViewModelProvider(this)[MeetingViewModel::class.java]
        initViews()
        setListener()
    }

    private fun initViews() {
        if(intent.getStringExtra("type")=="edit"){
            binding.etMeetingName.setText(Veriables.currentMeeting!!.meetingtitle)
            binding.etMeetingLink.setText(Veriables.currentMeeting!!.meetinglink)
            binding.etMeetingAttendee.setText(Veriables.currentMeeting!!.attandeeUserEmails)
            binding.tvStartTime.text = Veriables.currentMeeting!!.starttime
            binding.tvEndTime.text = Veriables.currentMeeting!!.endtime
            binding.tvMeetingDate.text = Veriables.currentMeeting!!.meetingdate
            binding.ivAddMeeting.text = "Update Meeting"
            binding.tvHeading.text = "Update Meeting"
        }
    }

    private fun setListener() {
        binding.ivAddMeeting.setOnClickListener {
            if (checkFrom()) {
                Log.d(Veriables.TAG, "setListener: ${Veriables.currentMeeting.toString()}")
                if (binding.ivAddMeeting.text.toString()=="Update Meeting"){
                    val updateMeetingRequest = UpdateMeetingRequest(
                        binding.etMeetingAttendee.text.toString(),
                        binding.tvEndTime.text.toString(),
                        Veriables.addUserResponse!!.userEmail,
                        Veriables.currentMeeting!!.id!!,
                        binding.tvMeetingDate.text.toString(),
                        binding.etMeetingLink.text.toString(),
                        binding.etMeetingName.text.toString(),
                        binding.tvStartTime .text.toString()
                    )
                    Functions.showProgressDialog(this@MeetingActivity, "Updating Meeting")
                    meetingViewModel.updateMeeting(updateMeetingRequest)
                }else{
                    val createMeetingRequest = CreateMeetingRequest(
                        binding.etMeetingAttendee.text.toString(),
                        binding.tvEndTime.text.toString(),
                        Veriables.addUserResponse!!.userEmail,
                        binding.tvMeetingDate.text.toString(),
                        binding.etMeetingName.text.toString(),
                        binding.tvStartTime.text.toString(),
                        binding.etMeetingLink.text.toString()
                    )
                    Functions.showProgressDialog(this@MeetingActivity, "Creating Meeting")
                    meetingViewModel.createMeeting(createMeetingRequest)
                }


            }
        }

        binding.tvStartTime.setOnClickListener {
            showStartTimePickerDialog()
        }

        binding.tvEndTime.setOnClickListener {
            showEndTimePickerDialog()
        }

        binding.tvMeetingDate.setOnClickListener {
            showDatePickerDialog()
        }

        meetingViewModel.meetingCreated.observe(this) {
            when (it) {
                "Created" -> {
                    Functions.dismissProgressDialog()
                    displayToast("Created Meeting Successfully")
                    onBackPressed()
                    finish()
                }
                "Error" -> {
                    Functions.dismissProgressDialog()
                    displayToast("Something went Wrong..Try other time slot")
                }
                "Updated"->{
                    Functions.dismissProgressDialog()
                    displayToast("updated Meeting Successfully")
                    onBackPressed()
                    finish()
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val mDatePicker = com.digitalgenius.meetingapp.utilities.DatePicker()
        mDatePicker.show(supportFragmentManager, "Date Pick")
    }

    private fun showStartTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this@MeetingActivity,
            startTimePickerDialogListener,
            12,
            10,
            true
        )
        timePickerDialog.show()
    }

    private fun showEndTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this@MeetingActivity,
            endTimePickerDialogListener,
            12,
            10,
            true
        )
        timePickerDialog.show()
    }

    private val startTimePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            binding.tvStartTime.text = "${hourOfDay}:${minute}:00"
        }

    private val endTimePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            binding.tvEndTime.text = "${hourOfDay}:${minute}:00"
        }

    private fun checkFrom(): Boolean {
        if (binding.etMeetingName.text.toString().isEmpty()) {
            displayToast("Enter the meeting title")
            return false
        } else if (binding.etMeetingLink.text.toString().isEmpty()) {
            displayToast("Enter the meeting link")
            return false
        } else if (binding.etMeetingAttendee.text.toString().isEmpty()) {
            displayToast("Enter the meeting attendee emails")
            return false
        } else if (binding.tvStartTime.text.toString().isEmpty() || binding.tvStartTime.text.equals(
                "Start Time"
            )
        ) {
            displayToast("Enter the meeting start time")
            return false
        } else if (binding.tvEndTime.text.toString()
                .isEmpty() || binding.tvEndTime.text.equals("End Time")
        ) {
            displayToast("Enter the meeting end time")
            return false
        } else if (binding.tvMeetingDate.text.toString()
                .isEmpty() || binding.tvMeetingDate.text!!.equals("Meeting Date")
        ) {
            displayToast("Enter the meeting date")
            return false
        }

        return true
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val mCalendar: Calendar = Calendar.getInstance()
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val date = mCalendar.time
        val df = android.text.format.DateFormat.format("yyyy-MM-dd", date)
        val selectedDate: String = df.toString()
        binding.tvMeetingDate.text = selectedDate
    }

}