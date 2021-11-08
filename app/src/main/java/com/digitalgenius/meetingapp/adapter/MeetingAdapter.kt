package com.digitalgenius.meetingapp.adapter

import android.content.Context
import com.digitalgenius.meetingapp.interfaces.MeetingClickListener
import androidx.recyclerview.widget.RecyclerView
import com.digitalgenius.meetingapp.adapter.MeetingAdapter.meetingViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.digitalgenius.meetingapp.databinding.ItemMeetingBinding

class MeetingAdapter(private val context: Context, private val clickListener: MeetingClickListener) : RecyclerView.Adapter<meetingViewHolder>() {

    private var binding: ItemMeetingBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): meetingViewHolder {
        binding = ItemMeetingBinding.inflate(LayoutInflater.from(context), parent, false
        )
        return meetingViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: meetingViewHolder, position: Int) {
        binding!!.mainContainer.setOnClickListener { v: View? -> clickListener.onClick(position) }
        binding!!.tvDetails.setOnClickListener { v: View? -> clickListener.onClick(position) }
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class meetingViewHolder(itemView: ItemMeetingBinding) :
        RecyclerView.ViewHolder(itemView.root)
}