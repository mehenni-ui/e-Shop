package com.example.elalmashop.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.elalmashop.databinding.CartListItemBinding
import com.example.elalmashop.data.model.Product

class CartAdapter(val context: Context): RecyclerView.Adapter<CartAdapter.CartItemViewHolder>() {


    var onItemClick: ((Product) -> Unit)? = null
    var onIncreaseProductNumberClickListener: ((Int) -> Unit)? = null
    var onDecreaseProductNumberClickListener: ((Int) -> Unit)? = null




    inner class CartItemViewHolder(val binding: CartListItemBinding) : ViewHolder(binding.root)


    private val differCallBack = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(CartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val product = differ.currentList[position]

        Glide.with(context).load(product.thumbnail).into(holder.binding.imgCartItemId)
        holder.binding.titleCartItemId.text = product.title
        holder.binding.brandCartItemId.text = product.brand
        holder.binding.productNumberCartItemId.text = product.productNumber.toString()
        holder.binding.priceCartItemId.text = "${product.price}$"

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }

        holder.binding.btnAddCartItemId.setOnClickListener {
            Log.d("item_size", itemCount.toString())
            onIncreaseProductNumberClickListener?.invoke(product.id)
        }

        holder.binding.btnMinusCartItemId.setOnClickListener {
            onDecreaseProductNumberClickListener?.invoke(product.id)
        }
    }
}