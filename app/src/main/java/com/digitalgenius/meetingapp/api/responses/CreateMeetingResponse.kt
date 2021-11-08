package com.digitalgenius.meetingapp.api.responses


import com.google.gson.annotations.SerializedName

data class CreateMeetingResponse(
    @SerializedName("attandeeUserEmails")
    val attandeeUserEmails: String,
    @SerializedName("endtime")
    val endtime: String,
    @SerializedName("hostUserEmail")
    val hostUserEmail: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("meetingdate")
    val meetingdate: String,
    @SerializedName("meetingtitle")
    val meetingtitle: String,
    @SerializedName("starttime")
    val starttime: String,
    @SerializedName("meetinglink")
    val meetinglink: String
)