package com.example.apptesteframeworkdiego.model

import com.google.gson.annotations.SerializedName

data class AlbumsResponseModel (
    @SerializedName("userId")
    var  userId : Int,

    @SerializedName("id")
    var  id : Int,

    @SerializedName("title")
    var title :String
)
