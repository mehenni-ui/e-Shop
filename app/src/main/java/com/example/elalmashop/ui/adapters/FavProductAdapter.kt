package com.example.elalmashop.ui.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.elalmashop.data.model.FavProduct
import com.example.elalmashop.databinding.FavProductItemBinding

class FavProductAdapter(val context: Context):  RecyclerView.Adapter<FavProductAdapter.FavProductVieHolder>() {

    var onFavItemClickListener: ((FavProduct) -> Unit)? = null

    inner class FavProductVieHolder(val binding: FavProductItemBinding) : ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<FavProduct>(){
        override fun areItemsTheSame(oldItem: FavProduct, newItem: FavProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavProduct, newItem: FavProduct): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavProductVieHolder {
        return FavProductVieHolder(FavProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavProductVieHolder, position: Int) {
        val favProduct = differ.currentList[position]

        if (favProduct.isFavorite){
            holder.binding.btnFavProductItemFavoriteId.setColorFilter(Color.RED)
        }else{
            holder.binding.btnFavProductItemFavoriteId.setColorFilter(Color.BLACK)
        }

        Glide.with(context).load(favProduct.thumbnail).into(holder.binding.imageFavProductItemId)
        holder.binding.txtFavProductItemPriceId.text = favProduct.price.toString()
        holder.binding.txtFavProductItemTitleId.text = favProduct.title


        holder.binding.btnFavProductItemFavoriteId.setOnClickListener {
            onFavItemClickListener?.invoke(favProduct)
        }


    }

}