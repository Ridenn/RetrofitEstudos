package com.example.retrofitestudo

import com.example.retrofitestudo.Model.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductResponse<T> {

    //método genérico
    fun success(response : T)
}