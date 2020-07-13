package com.ricardo.imagesapp.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class ProfileImage(

        @SerializedName("small")
        var small: String = "",
        @SerializedName("medium")
        var medium: String = "",
        @SerializedName("large")
        var large: String = ""
): RealmObject()