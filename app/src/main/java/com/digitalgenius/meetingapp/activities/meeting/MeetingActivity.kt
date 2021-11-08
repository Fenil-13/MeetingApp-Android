package com.digitalgenius.meetingapp.activities.meeting

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.api.requests.CreateMeetingRequest
import com.digitalgenius.meetingapp.databinding.ActivityMeetingBinding
import com.digitalgenius.meetingapp.utilities.Functions
import com.digitalgenius.meetingapp.utilities.Veriables
import com.digitalgenius.meetingapp.utilities.displayToast
import java.text.DateFormat
import java.util.*

class MeetingActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityMeetingBinding
    private lateinit var meetingViewModel: MeetingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        meetingViewModel = ViewModelProvider(this)[MeetingViewModel::class.java]
        setListener()
    }

    private fun setListener() {
        binding.ivAddMeeting.setOnClickListener {
            onBackPressed()
            finish()
        }
        binding.ivAddMeeting.setOnClickListener {
            if (checkFrom()) {
                val createMeetingRequest = CreateMeetingRequest(
                    "fjmoradiya@gmail.com",
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
            return false
        } else if (binding.etMeetingLink.text.toString().isEmpty()) {
            return false
        } else if (binding.etMeetingAttendee.text.toString().isEmpty()) {
            return false
        } else if (binding.tvStartTime.text.toString().isEmpty() && binding.tvStartTime.text.equals(
                "Start Time"
            )
        ) {
            return false
        } else if (binding.tvEndTime.text.toString()
                .isEmpty() && binding.tvEndTime.text.equals("End Time")
        ) {
            return false
        } else if (binding.tvMeetingDate.text.toString()
                .isEmpty() && binding.tvMeetingDate.text!!.equals("Meeting Date")
        ) {
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