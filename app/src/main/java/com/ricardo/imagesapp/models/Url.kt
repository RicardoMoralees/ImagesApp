package com.ricardo.imagesapp.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Url (
        @SerializedName("raw")
        var raw : String = "",
        @SerializedName("full")
        var full : String = "",
        @SerializedName("regular")
        var regular : String = "",
        @SerializedName("small")
        var small : String = "",
        @SerializedName("thumb")
        var thumb : String = ""
): RealmObject()