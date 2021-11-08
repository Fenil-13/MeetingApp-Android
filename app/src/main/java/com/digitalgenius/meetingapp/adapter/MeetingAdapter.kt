package com.digitalgenius.meetingapp.adapter

import android.content.Context
import com.digitalgenius.meetingapp.interfaces.MeetingClickListener
import androidx.recyclerview.widget.RecyclerView
import com.digitalgenius.meetingapp.adapter.MeetingAdapter.meetingViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.databinding.ItemMeetingBinding

class MeetingAdapter(
    private val context: Context,
    private val data: ArrayList<CreateMeetingResponse>,
    private val clickListener: MeetingClickListener
) : RecyclerView.Adapter<meetingViewHolder>() {

    private var binding: ItemMeetingBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): meetingViewHolder {
        binding = ItemMeetingBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return meetingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: meetingViewHolder, position: Int) {
        binding?.apply {
            tvMeetingTitle.text=data[position].meetingtitle
            tvMeetingTime.text="${data[position].starttime} - ${data[position].endtime}"
            tvMeetingDate.text=data[position].meetingdate
            tvMeetingDesc.text=data[position].hostUserEmail
            tvDetails.setOnClickListener { v: View? -> clickListener.onClick(position) }
            ivDelete.setOnClickListener {
                clickListener.onDelete(position)
            }
            ivEdit.setOnClickListener {
                clickListener.onUpdate(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class meetingViewHolder(itemView: ItemMeetingBinding) :
        RecyclerView.ViewHolder(itemView.root)
}