package com.digitalgenius.meetingapp.api.responses


import com.google.gson.annotations.SerializedName

data class AddUserResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userAuthid")
    val userAuthid: String,
    @SerializedName("userEmail")
    val userEmail: String,
    @SerializedName("userName")
    val userName: String
)