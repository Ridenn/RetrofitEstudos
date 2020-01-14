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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //'lateinit var' indica que a variavel será inicializada depois
    lateinit var adapter : ProductsAdapter
    val products : ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureRecyclerView()

        listAll()

        fab_add_product.setOnClickListener {
            AlertDialogUtil(this, window.decorView as ViewGroup).show {
                adapter.add(it)
                listAll()
            }
        }
    }

    private fun configureRecyclerView() {

        val recyclerView = rv_products
        adapter = ProductsAdapter(products, this) { product, position ->
            AlertDialogUtil(this, window.decorView as ViewGroup).alter(product){
                adapter.update(it, position)
            }
        }

        recyclerView.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    fun listAll(){
        ProductWebClient().list(object: ProductResponse<ArrayList<Product>>{
            override fun success(response: ArrayList<Product>) {
                adapter.addAll(response)
            }
        })
    }
}
