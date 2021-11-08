package com.digitalgenius.meetingapp.interfaces

interface MeetingClickListener {
    fun onClick(position: Int)
    fun onDelete(position: Int)
    fun onUpdate(position: Int)
    fun onShare(position: Int)
}