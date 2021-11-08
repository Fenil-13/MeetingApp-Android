package com.digitalgenius.meetingapp.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalgenius.meetingapp.api.MeetingAppClient
import com.digitalgenius.meetingapp.api.responses.AddUserResponse
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.api.responses.GetAllMeetingResponse
import com.digitalgenius.meetingapp.utilities.Veriables
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {
    var dataFetched: MutableLiveData<String> = MutableLiveData<String>("Not Called")
    var data :ArrayList<CreateMeetingResponse> = ArrayList()
    fun getAllMeetings(addUserResponse: AddUserResponse?) {
        val response = MeetingAppClient.publicApi.getAllMeetings(addUserResponse!!)
        response.enqueue(object :
            retrofit2.Callback<GetAllMeetingResponse> {

            override fun onResponse(
                call: Call<GetAllMeetingResponse>,
                response: Response<GetAllMeetingResponse>
            ) {
                if(response.code()==200){
                    Log.d(Veriables.TAG, "onResponse: ${response.body()!!.size}")
                    dataFetched.postValue("Get_data")
                    data=response.body()!!
                }else if(response.code()==404){
                    dataFetched.postValue("Get_data")
                    data=ArrayList();
                }else{
                    dataFetched.postValue("Error")
                }

            }

            override fun onFailure(call: Call<GetAllMeetingResponse>, t: Throwable) {

            }

        })
    }

    fun deleteMeeting(createMeetingResponse: CreateMeetingResponse) {
        val response = MeetingAppClient.publicApi.deleteMeeting(createMeetingResponse)
        response.enqueue(object :
            retrofit2.Callback<Void> {

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.d(Veriables.TAG, "onResponse: ${response.raw()}")
                if (response.code()==200) {
                    dataFetched.postValue("delete")
                } else {
                    dataFetched.postValue("Error")
                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d(Veriables.TAG, "onResponse: ${t.message}")
            }

        })
    }

}