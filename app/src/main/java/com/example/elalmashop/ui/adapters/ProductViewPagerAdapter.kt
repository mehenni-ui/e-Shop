package com.example.elalmashop.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.elalmashop.databinding.ViewPagerDetailsProductImageBinding

class ProductViewPagerAdapter(private val context: Context, private val images: List<String>): RecyclerView.Adapter<ProductViewPagerAdapter.ProductViewPagerViewHolder>() {

    inner class ProductViewPagerViewHolder(val binding: ViewPagerDetailsProductImageBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewPagerViewHolder {
        return ProductViewPagerViewHolder(ViewPagerDetailsProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ProductViewPagerViewHolder, position: Int) {
        val imageProduct = images[position]
        Glide.with(context)
            .load(imageProduct)
            .into(holder.binding.productDetailsImagesId)
        //holder.binding.productDetailsImagesId.setImageURI(Uri.parse(imageProduct))
    }
}