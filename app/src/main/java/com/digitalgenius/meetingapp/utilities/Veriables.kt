package com.digitalgenius.meetingapp.utilities

import com.digitalgenius.meetingapp.api.responses.AddUserResponse
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.google.firebase.auth.FirebaseUser

object Veriables {
    var firebaseUser: FirebaseUser?=null
    var addUserResponse: AddUserResponse?=null
    var currentMeeting: CreateMeetingResponse?=null
    val TAG="MeetingTag"
}