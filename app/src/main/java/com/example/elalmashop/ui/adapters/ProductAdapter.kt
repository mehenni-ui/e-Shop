package com.example.elalmashop.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.elalmashop.databinding.ProductItemViewBinding
import com.example.elalmashop.data.model.Product

class ProductAdapter(val context: Context): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClickListener : ((Product) ->Unit)? = null
    var onFavIconClickListener: ((Product) -> Unit)? = null

    private val differCallBack = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer (this, differCallBack)


    inner class ProductViewHolder(val binding: ProductItemViewBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.txtProductItemTitleId.text = product.title
        holder.binding.txtProductItemPriceId.text = product.price.toString()
        Glide.with(context).load(product.thumbnail).into(holder.binding.imageProductItemId)


        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(product)
        }

        holder.binding.btnProductItemFavoriteId.setOnClickListener {
            onFavIconClickListener?.invoke(product)
        }

    }
}