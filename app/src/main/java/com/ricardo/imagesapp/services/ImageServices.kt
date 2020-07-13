package com.ricardo.imagesapp.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.utils.Constants
import io.realm.Realm
import io.realm.kotlin.delete
import io.realm.kotlin.where
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ImageServices {

    companion object {
         fun getHomeImages(context: Context, page: Int, callback: ImagesInterface){

            val request = ServiceBuilder.buildService(ImageInterface::class.java)
            val call = request.getHomeImages(Constants.ACCESS_KEY, page)

            call.enqueue(object : Callback<List<Image>> {
                override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                    if (response.isSuccessful && response.body() != null){
                        val realm = Realm.getDefaultInstance()
                        val imagenes = response.body()

                        if (imagenes != null) {
                            imagenes.forEach{imagen ->
                                if (imagen.description.isNullOrEmpty()){
                                    imagen.description = ""
                                }
                                if (imagen.alt_description.isNullOrEmpty()){
                                    imagen.alt_description = ""
                                }
                                if (imagen.user?.bio.isNullOrEmpty()){
                                    imagen.user?.bio = ""
                                }
                                if (imagen.user?.location.isNullOrEmpty()){
                                    imagen.user?.location = ""
                                }
                            }
                        }

                        realm.beginTransaction()
                        realm.copyToRealmOrUpdate(imagenes)
                        realm.commitTransaction()
                        callback.onImagesResponse(response.body().orEmpty())
                    } else {
                        callback.onImagesFail()
                    }
                }
                override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        fun getUserPhotos(context: Context, username: String, page: Int, callback: ImagesInterface){

            val request = ServiceBuilder.buildService(ImageInterface::class.java)
            val call = request.getUserPhotos(Constants.ACCESS_KEY, username, page)

            call.enqueue(object : Callback<List<Image>> {
                override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                    if (response.isSuccessful && response.body() != null){
                        callback.onImagesResponse(response.body()!!)
                    } else {
                        callback.onImagesFail()
                    }
                }
                override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    interface ImagesInterface {
        fun onImagesResponse(images: List<Image>)
        fun onImagesFail()
    }
}