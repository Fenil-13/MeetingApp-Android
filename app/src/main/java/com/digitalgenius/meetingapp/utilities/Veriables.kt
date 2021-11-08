package com.digitalgenius.meetingapp.utilities

import com.digitalgenius.meetingapp.api.responses.AddUserResponse
import com.google.firebase.auth.FirebaseUser

object Veriables {
    var firebaseUser: FirebaseUser?=null
    var addUserResponse: AddUserResponse?=null
    val TAG="MeetingTag"
}