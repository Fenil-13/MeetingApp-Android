package com.digitalgenius.meetingapp.api.requests


import com.google.gson.annotations.SerializedName

data class GetMeetingByDateRequest(
    @SerializedName("attandeeUserEmails")
    val attandeeUserEmails: String,
    @SerializedName("endtime")
    val endtime: String?,
    @SerializedName("hostUserEmail")
    val hostUserEmail: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("meetingdate")
    val meetingdate: String,
    @SerializedName("meetinglink")
    val meetinglink: String,
    @SerializedName("meetingtitle")
    val meetingtitle: String,
    @SerializedName("starttime")
    val starttime: String?
)