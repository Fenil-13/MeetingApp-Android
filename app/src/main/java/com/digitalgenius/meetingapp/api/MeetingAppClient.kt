package com.digitalgenius.meetingapp.api

import com.digitalgenius.meetingapp.api.services.MeetingAppAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MeetingAppClient {
    val BASE_URL="https://meetingschedulerbackend.herokuapp.com"
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val publicApi = retrofitBuilder.build().create(MeetingAppAPI::class.java)
}