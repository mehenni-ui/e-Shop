package com.example.elalmashop.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.elalmashop.databinding.CategoryItemViewBinding

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    private var selectedPosition: Int = -1

    private val differCallBack = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = differ.currentList[position]
        holder.binding.txtCategoryItemId.text = categoryItem

        if (selectedPosition == position) {
            holder.binding.txtCategoryItemId.setBackgroundColor(Color.RED)
            holder.binding.txtCategoryItemId.setTextColor(Color.WHITE)
        } else {
            holder.binding.txtCategoryItemId.setBackgroundColor(Color.LTGRAY)
            holder.binding.txtCategoryItemId.setTextColor(Color.BLACK)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(categoryItem)
            selectedPosition = holder.adapterPosition
            Log.d("position_item", position.toString())
            Log.d("selectedPosition_item", selectedPosition.toString())
            notifyDataSetChanged()
        }






    }



    inner class CategoryViewHolder(val binding: CategoryItemViewBinding) : ViewHolder(binding.root)
}