package com.digitalgenius.meetingapp.api.requests


import com.google.gson.annotations.SerializedName

data class AddUserRequest(
    @SerializedName("userAuthid")
    val userAuthid: String,
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("userName")
    val userName: String
)