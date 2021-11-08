package com.digitalgenius.meetingapp.activities.meeting

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalgenius.meetingapp.api.MeetingAppClient
import com.digitalgenius.meetingapp.api.requests.CreateMeetingRequest
import com.digitalgenius.meetingapp.api.requests.UpdateMeetingRequest
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.utilities.Veriables
import retrofit2.Call
import retrofit2.Response

class MeetingViewModel:ViewModel() {
    var meetingCreated: MutableLiveData<String> = MutableLiveData<String>("Not Called")
    fun createMeeting(createMeetingRequest: CreateMeetingRequest) {


            val response = MeetingAppClient.publicApi.createMeeting(createMeetingRequest)
            response.enqueue(object :
                retrofit2.Callback<CreateMeetingResponse> {
                override fun onResponse(
                    call: Call<CreateMeetingResponse>,
                    response: Response<CreateMeetingResponse>
                ) {
                    if(response.isSuccessful){
                        Log.d(Veriables.TAG, "onResponse: ${response.body()?.id}")
                        meetingCreated.postValue("Created")
                    }else{
                        meetingCreated.postValue("Error")
                    }

                }

                override fun onFailure(call: Call<CreateMeetingResponse>, t: Throwable) {
                    meetingCreated.postValue("Error")
                }

            })

    }

    fun updateMeeting(updateMeetingRequest: UpdateMeetingRequest) {
        val response = MeetingAppClient.publicApi.updateMeeting(updateMeetingRequest)
        response.enqueue(object :
            retrofit2.Callback<CreateMeetingResponse> {
            override fun onResponse(
                call: Call<CreateMeetingResponse>,
                response: Response<CreateMeetingResponse>
            ) {
                Log.d(Veriables.TAG, "onResponse: ${response.raw()}")
                if(response.isSuccessful){
                    Log.d(Veriables.TAG, "onResponse: ${response.body()?.id}")
                    meetingCreated.postValue("Updated")
                }else{
                    meetingCreated.postValue("Error")
                }

            }

            override fun onFailure(call: Call<CreateMeetingResponse>, t: Throwable) {
                Log.d(Veriables.TAG, "onResponse: ${t.message}")
                meetingCreated.postValue("Error")
            }
        })
    }
}