package com.example.apptesteframeworkdiego.model

import com.google.gson.annotations.SerializedName

data class TodosResponseModel (
    @SerializedName("userId")
    var  userId : Int,

    @SerializedName("id")
    var  id : Int,

    @SerializedName("title")
    var title :String,

    @SerializedName("title")
    var completed :Boolean
)
