package com.example.retrofitestudo.Service

import com.example.retrofitestudo.Model.Product
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProductService {

    @GET("products")
    fun list() : Call<ArrayList<Product>>

    //@Body serve para passar o produto no corpo da requisição
    @POST("products")
    fun insert(@Body product: Product) : Call<Product>

    @PUT("products/{id}")
    fun alter(@Body product : Product, @Path("id") id : String) : Call<Product>

    @DELETE("products/{id}")
    fun delete(@Path("id") id : String) : Call<ResponseBody>
}