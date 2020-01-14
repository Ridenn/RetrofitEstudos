package com.example.retrofitestudo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retrofitestudo.Config.RetrofitConfig
import com.example.retrofitestudo.Model.Product
import com.karima.retrofit.ProductsAdapter
import com.karima.retrofit.utils.AlertDialogUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_list_layout.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductWebClient() {

    fun list(productResponse : ProductResponse<ArrayList<Product>>){
        val call = RetrofitConfig().productService().list()
        call.enqueue(object : Callback<ArrayList<Product>>{
            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                print(t)
            }

            override fun onResponse(call: Call<ArrayList<Product>>, response: Response<ArrayList<Product>>) {
                response.body() ?.let {
                    productResponse.success(it)
                }
            }
        })
    }

    fun insert(product: Product, productResponse: ProductResponse<Product>){
        val call = RetrofitConfig().productService().insert(product)
        call.enqueue(object : Callback<Product>{
            override fun onFailure(call: Call<Product>, t: Throwable) {
                print(t)
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                response.body() ?.let {
                    productResponse.success(it)
                }
            }
        })
    }

    fun alter(product : Product, callResponse : (products : Product?, throwable : Throwable?) -> Unit){
        val call = product.id?.let { RetrofitConfig().productService().alter(product, it) }
        if (call != null) {
            call.enqueue(object : Callback<Product>{
                override fun onFailure(call: Call<Product>, t: Throwable) {
                    print(t)
                }

                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    callResponse(response.body(), null)
                }
            })
        }
    }

    fun delete(id : String, callResponse : (response : ResponseBody, throwable : Throwable?) -> Unit){
        val call = RetrofitConfig().productService().delete(id)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                print(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response.body()?.let{
                    callResponse(it, null)
                }
            }
        })
    }
}
