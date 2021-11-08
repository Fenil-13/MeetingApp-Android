package com.digitalgenius.meetingapp.api.services

import com.digitalgenius.meetingapp.api.requests.AddUserRequest
import com.digitalgenius.meetingapp.api.requests.CreateMeetingRequest
import com.digitalgenius.meetingapp.api.requests.GetMeetingByDateRequest
import com.digitalgenius.meetingapp.api.requests.UpdateMeetingRequest
import com.digitalgenius.meetingapp.api.responses.AddUserResponse
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.api.responses.GetAllMeetingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MeetingAppAPI {

    @Headers( "Content-Type: application/json" )
    @POST("/adduser")
    fun addUser(@Body addUserRequest: AddUserRequest
    ): Call<AddUserResponse>


    @Headers( "Content-Type: application/json" )
    @POST("/finduser")
    fun findUser(@Body addUserRequest: AddUserRequest
    ): Call<AddUserResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/createmeeting")
    fun createMeeting(@Body createMeetingRequest: CreateMeetingRequest
    ): Call<CreateMeetingResponse>


    @Headers("Content-Type: application/json")
    @POST("/getallmeetings")
    fun getAllMeetings(@Body addUserResponse: AddUserResponse):Call<GetAllMeetingResponse>

    @Headers("Content-Type: application/json")
    @POST("/deletemeeting")
    fun deleteMeeting(@Body createMeetingResponse: CreateMeetingResponse):Call<Void>

    @Headers( "Content-Type: application/json" )
    @POST("/updatemeeting")
    fun updateMeeting(@Body updateMeetingRequest: UpdateMeetingRequest
    ): Call<CreateMeetingResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/getallmeetingsbydate")
    fun getAllMeetingByDate(@Body getMeetingByDateRequest: GetMeetingByDateRequest
    ): Call<GetAllMeetingResponse>

}