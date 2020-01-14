package com.karima.retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitestudo.Model.Product
import com.example.retrofitestudo.ProductWebClient
import com.example.retrofitestudo.R
import kotlinx.android.synthetic.main.product_list_layout.view.*

class ProductsAdapter(
    private var products: ArrayList<Product>,
    private val context: Context,
    private val onItemClickListener: (product: Product, position: Int) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = this.products[position]
        holder.bindView(product, context)
        holder.itemView.iv_product.setOnClickListener {
            onItemClickListener(product, position)
        }
        holder.itemView.tv_product_description.setOnClickListener {
            onItemClickListener(product, position)
        }
        holder.itemView.tv_product_name.setOnClickListener {
            onItemClickListener(product, position)
        }

        holder.itemView.bt_delete.setOnClickListener {
            product.id?.let { it1 ->
                ProductWebClient().delete(it1) { response, throwable ->
                    response.let {
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                        delete(position)
                    }
                    throwable?.let {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(product: Product, context: Context) {
            Glide.with(context)
                .load(product.image)
                .into(itemView.iv_product)
            itemView.tv_product_name.text = product.title
            itemView.tv_product_description.text = product.description

        }

    }

    fun getList(): ArrayList<Product> {
        return products
    }

    fun addAll(products: ArrayList<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun add(product: Product) {
        this.products.add(product)
        notifyDataSetChanged()
    }

    fun update(product: Product, position: Int) {
        this.products[position] = product
        notifyItemChanged(position)
    }

    private fun delete(position: Int) {
        this.products.removeAt(position)
        notifyDataSetChanged()
    }
}

