package com.digitalgenius.meetingapp.fragments

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.digitalgenius.meetingapp.R
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.interfaces.MeetingDialogListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DialogMeetingFragment internal constructor(
    private val listener: MeetingDialogListener,
    private val meetingResponse: CreateMeetingResponse
) : DialogFragment() {
    private var root_view: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root_view = inflater.inflate(R.layout.dialog_meeting, container, false)
        (root_view?.findViewById<View>(R.id.tvTitle) as TextView).text = meetingResponse.meetingtitle
        (root_view?.findViewById<View>(R.id.tvMeetingDate) as TextView).text = meetingResponse.meetingdate
        (root_view?.findViewById<View>(R.id.tvStartTime) as TextView).text = meetingResponse.starttime
        (root_view?.findViewById<View>(R.id.tvEndTime) as TextView).text = meetingResponse.endtime
        (root_view?.findViewById<View>(R.id.tvMeetingLink) as TextView).setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meetingResponse.meetinglink)))
            }catch (e:Exception){
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }
        (root_view?.findViewById<View>(R.id.btnClose) as FloatingActionButton).setOnClickListener {
            dismiss()
            listener.onClick()
        }
        return root_view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}