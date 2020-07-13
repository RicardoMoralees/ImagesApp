package com.ricardo.imagesapp.services;

import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.models.User
import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageInterface {

    @GET("/photos")
    fun getHomeImages(
            @Header("Authorization") key: String,
            @Query("page") page: Int): Call<List<Image>>

    @GET("/users/{username}")
    fun getUserProfile(
            @Header("Authorization") key: String,
            @Path("username") username: String
    ): Call<User>

    @GET("/users/{username}/photos")
    fun getUserPhotos(
            @Header("Authorization") key: String,
            @Path("username") username: String,
            @Query("page") page: Int
    ): Call<List<Image>>
}
