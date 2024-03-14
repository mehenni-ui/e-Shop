package com.example.elalmashop.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.elalmashop.R
import com.example.elalmashop.data.db.local.ProductDb
import com.example.elalmashop.data.repo.FavProductRepo
import com.example.elalmashop.databinding.FragmentFavoriteBinding
import com.example.elalmashop.ui.adapters.FavProductAdapter
import com.example.elalmashop.ui.adapters.ProductAdapter
import com.example.elalmashop.ui.viewModels.FavProductViewModel
import com.example.elalmashop.ui.viewModels.FavProductViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favProductRepo: FavProductRepo
    private lateinit var favProductViewModelFactory: FavProductViewModelFactory
    private lateinit var favProductViewModel: FavProductViewModel
    private lateinit var favProductAdapter: FavProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favProductRepo = FavProductRepo(ProductDb(requireContext()))
        favProductViewModelFactory = FavProductViewModelFactory(favProductRepo)
        favProductViewModel = ViewModelProvider(this, favProductViewModelFactory)[FavProductViewModel::class.java]

        favProductAdapter = FavProductAdapter(requireContext())



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)


        binding.rvFavoriteProductId.layoutManager = GridLayoutManager(requireContext(), 2)

        favProductViewModel.getAllFavProduct().observe(viewLifecycleOwner){
            favProductAdapter.differ.submitList(it)
            binding.rvFavoriteProductId.adapter = favProductAdapter

        }

        favProductAdapter.onFavItemClickListener = {favProduct ->
            favProductViewModel.deleteFavProduct(favProduct)

            Snackbar.make(view, "Item removed From Favorite", Snackbar.LENGTH_LONG)
                .setAction("Undo"){
                    favProductViewModel.upsertFavProduct(favProduct)
                }
                .show()
        }


    }


}