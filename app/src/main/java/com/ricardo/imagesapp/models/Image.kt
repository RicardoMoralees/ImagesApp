package com.ricardo.imagesapp.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Image(

        @PrimaryKey
        @SerializedName("id")
        var id: String = "",
        @SerializedName("description")
        var description: String = "",
        @SerializedName("alt_description")
        var alt_description: String = "",
        @SerializedName("urls")
        var urls: Url? = null,
        @SerializedName("likes")
        var likes: Int = 0,
        @SerializedName("liked_by_user")
        var liked_by_user: Boolean = false,
        @SerializedName("user")
        var user: User? = null,
        var esFavorita: Boolean = false

): RealmObject()