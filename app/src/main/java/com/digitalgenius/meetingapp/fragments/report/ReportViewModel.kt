package com.digitalgenius.meetingapp.fragments.report

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalgenius.meetingapp.api.MeetingAppClient
import com.digitalgenius.meetingapp.api.requests.GetMeetingByDateRequest
import com.digitalgenius.meetingapp.api.responses.CreateMeetingResponse
import com.digitalgenius.meetingapp.api.responses.GetAllMeetingResponse
import com.digitalgenius.meetingapp.utilities.Veriables
import retrofit2.Call
import retrofit2.Response

class ReportViewModel : ViewModel() {
    var dataRetrived: MutableLiveData<String> = MutableLiveData<String>("Not Called")
    var data :ArrayList<CreateMeetingResponse> = ArrayList()
    fun get_daily_data(date:String) {
        val getMeetingByDateRequest= GetMeetingByDateRequest(
            "",null,Veriables.addUserResponse?.userEmail!!
        ,"",date,"","",null
        );
        val response = MeetingAppClient.publicApi.getAllMeetingByDate(getMeetingByDateRequest)
        response.enqueue(object :
            retrofit2.Callback<GetAllMeetingResponse> {
            override fun onResponse(
                call: Call<GetAllMeetingResponse>,
                response: Response<GetAllMeetingResponse>
            ) {
                Log.d(Veriables.TAG, "onResponse: ${response.raw()}")
                if(response.code()==200){
                    Log.d(Veriables.TAG, "onResponse: ${response.body()?.size}")
                    data=response.body()!!
                    dataRetrived.postValue("Success")
                }else if (response.code()==404){
                    dataRetrived.postValue("Not Found")
                }else{
                    data= ArrayList()
                    dataRetrived.postValue("Error")
                }

            }

            override fun onFailure(call: Call<GetAllMeetingResponse>, t: Throwable) {
                Log.d(Veriables.TAG, "onFailure: ${t.message}")
                dataRetrived.postValue("Not Found")
            }

        })
    }
}