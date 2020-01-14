package com.karima.retrofit.utils

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.retrofitestudo.Model.Product
import com.example.retrofitestudo.ProductResponse
import com.example.retrofitestudo.ProductWebClient
import com.example.retrofitestudo.R
import kotlinx.android.synthetic.main.confirmation.view.*
import kotlinx.android.synthetic.main.form_product.view.*

class AlertDialogUtil(private val context: Context, private val viewGroup: ViewGroup) {

    fun show(created: (createdProduct: Product) -> Unit) {
        val createdView = LayoutInflater.from(context).inflate(
            R.layout.form_product,
            viewGroup,
            false
        )

        AlertDialog.Builder(context)
            .setTitle("Adicionar Produto")
            .setView(createdView)
            .setPositiveButton("salvar") { _, _ ->
                val name = createdView.form_product_title.text.toString()
                val description = createdView.form_product_description.text.toString()
                val price:Float = if (createdView.form_product_price.text.isNullOrEmpty()){
                    0F
                }else{
                    createdView.form_product_price.text.toString().toFloat()
                }

                val url = createdView.form_product_url.text.toString()

                val product = Product(null, title = name, description = description, price = price, image = url)
                ProductWebClient().insert(product, object : ProductResponse<Product>{
                    override fun success(response: Product) {
                        created(response)
                    }
                })
            }
            .show()
    }

    fun alter(product: Product, altered :(alteredProduct: Product) -> Unit){
        val createdView = LayoutInflater.from(context).inflate(
            R.layout.form_product,
            viewGroup,
            false
        )

        createdView.form_product_title.setText(product.title)
        createdView.form_product_description.setText(product.description)
        createdView.form_product_price.setText(product.price.toString())
        createdView.form_product_url.setText(product.image)

        AlertDialog.Builder(context)
            .setTitle("Alterar Produto")
            .setView(createdView)
            .setPositiveButton("Salvar") { _, _ ->
                val name = createdView.form_product_title.text.toString()
                val description = createdView.form_product_description.text.toString()
                val url = createdView.form_product_url.text.toString()
                val alteredProduct = product.copy(title =  name, description = description, image = url)
                ProductWebClient().alter(alteredProduct) { response, throwable ->
                    response?.let {
                        altered(it)
                    }
                    throwable?.let{
                        Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }

}
