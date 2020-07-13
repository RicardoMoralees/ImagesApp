package com.ricardo.imagesapp.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import org.jetbrains.annotations.NotNull

open class User (
        @SerializedName("id")
        var id: String = "",
        @SerializedName("username")
        var username: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("bio")
        var bio: String = "",
        @SerializedName("location")
        var location: String = "",
        @SerializedName("profile_image")
        var profile_image: ProfileImage? = null,
        @SerializedName("total_likes")
        var total_likes: Int = 0,
        @SerializedName("total_photos")
        var total_photos: Int = 0,
        @SerializedName("total_collections")
        var total_collections: Int = 0

): RealmObject()