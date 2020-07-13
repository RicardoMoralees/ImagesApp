package com.ricardo.imagesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.models.User
import com.ricardo.imagesapp.utils.Constants
import io.realm.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.where
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ProfileService {

    companion object {
         fun getUserProfile(context: Context, username: String, callback: ProfileInterface){

            val request = ServiceBuilder.buildService(ImageInterface::class.java)
            val call = request.getUserProfile(Constants.ACCESS_KEY, username)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful && response.body() != null){
                        callback.onProfileResponse(response.body()!!)
                    } else {
                        callback.onProfileFail()
                    }
                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    interface ProfileInterface {
        fun onProfileResponse(user: User)
        fun onProfileFail()
    }
}