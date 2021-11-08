package com.digitalgenius.meetingapp.activities.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalgenius.meetingapp.api.MeetingAppClient
import com.digitalgenius.meetingapp.api.requests.AddUserRequest
import com.digitalgenius.meetingapp.api.responses.AddUserResponse
import com.digitalgenius.meetingapp.utilities.Veriables
import retrofit2.Call
import retrofit2.Response

class RegistrationViewModel : ViewModel() {
    var userCreated: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    lateinit var addUserResponse: AddUserResponse

    fun call_create_user(uid: String, email: String, username: String) {
        val userRequest = AddUserRequest(
            uid,
            email,
            username
        )
        val response = MeetingAppClient.publicApi.addUser(userRequest)
        response.enqueue(object :
            retrofit2.Callback<AddUserResponse> {

            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                if (response.isSuccessful) {
                    addUserResponse = response.body()!!
                    Veriables.addUserResponse = response.body()
                    userCreated.postValue(true)
                } else {
                    userCreated.postValue(false)
                }

            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                userCreated.postValue(false)
            }

        })

    }
}