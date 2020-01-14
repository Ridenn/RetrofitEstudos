package com.example.retrofitestudo.Config

import com.example.retrofitestudo.Service.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {


    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.103:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //o '=' serve como 'return' e retorna a interface ProductService
    //cria a conexão acima com base no ProductService
    fun productService() : ProductService = retrofit.create(ProductService::class.java)

}