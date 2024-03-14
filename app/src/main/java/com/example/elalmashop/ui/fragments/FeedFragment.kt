package com.example.elalmashop.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elalmashop.R
import com.example.elalmashop.data.db.local.ProductDb
import com.example.elalmashop.data.model.FavProduct
import com.example.elalmashop.data.repo.FavProductRepo
import com.example.elalmashop.data.repo.ProductRepo
import com.example.elalmashop.ui.adapters.CategoryAdapter
import com.example.elalmashop.ui.adapters.ProductAdapter
import com.example.elalmashop.databinding.FragmentFeedBinding
import com.example.elalmashop.ui.viewModels.FavProductViewModel
import com.example.elalmashop.ui.viewModels.FavProductViewModelFactory
import com.example.elalmashop.ui.viewModels.FeedViewModel
import kotlinx.coroutines.launch


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var favProductRepo: FavProductRepo
    private lateinit var favProductViewModelFactory: FavProductViewModelFactory
    private lateinit var favProductViewModel: FavProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        binding.etFeedSearch.setOnClickListener {
            it.findNavController().navigate(R.id.searchFragment)
        }
        categoryAdapter = CategoryAdapter()
        productAdapter = ProductAdapter(requireContext())

        favProductRepo = FavProductRepo(ProductDb(requireContext()))
        favProductViewModelFactory = FavProductViewModelFactory(favProductRepo)
        favProductViewModel = ViewModelProvider(this, favProductViewModelFactory)[FavProductViewModel::class.java]






        return binding.root

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)

        binding.rvFeedCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeedCategory.adapter = categoryAdapter


            lifecycleScope.launch {
                feedViewModel.getAllCategories().observe(viewLifecycleOwner){
                    categoryAdapter.differ.submitList(it)
                }
            }

        categoryAdapter.onItemClickListener={category ->
            lifecycleScope.launch {
                feedViewModel.getProductsByCategory(category).observe(viewLifecycleOwner){productList ->
                    productAdapter.differ.submitList(productList.products)
                }
            }
        }




        binding.rvFeedNewCollection.layoutManager = GridLayoutManager(context,2)
        binding.rvFeedNewCollection.adapter = productAdapter

        lifecycleScope.launch {
            feedViewModel.getAllProduct().observe(viewLifecycleOwner){
                productAdapter.differ.submitList(it.products)
            }
        }
        productAdapter.onItemClickListener = {
            val bundle = Bundle().apply { putSerializable("product", it) }
            findNavController().navigate(R.id.detailsFragment, bundle)
        }

        productAdapter.onFavIconClickListener = {
            val favProduct = FavProduct(it.id, it.price, it.thumbnail, it.title, true)
            favProductViewModel.upsertFavProduct(favProduct)
            Log.d("fav_item", "fav item has been added")
        }



    }




}