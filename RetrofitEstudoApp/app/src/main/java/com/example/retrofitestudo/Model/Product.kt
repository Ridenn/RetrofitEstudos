package com.example.retrofitestudo.Model

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("_id")
    val id : String?,
    val title : String,
    val description : String,
    val price : Float,
    val image : String
)