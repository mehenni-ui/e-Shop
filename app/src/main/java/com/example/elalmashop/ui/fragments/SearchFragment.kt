package com.example.elalmashop.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.elalmashop.R
import com.example.elalmashop.databinding.FragmentSearchBinding
import com.example.elalmashop.ui.adapters.ProductAdapter
import com.example.elalmashop.ui.viewModels.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private val handler = Handler()
    private var runnable: Runnable? = null
    private lateinit var  productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        runnable = Runnable { performSearch("") }

        productAdapter = ProductAdapter(requireContext())

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacks(runnable!!)
                runnable = Runnable { performSearch(newText) }
                handler.postDelayed(runnable!!, 500)

                return true
            }

        })

        productAdapter.onItemClickListener={
            val bundle  = Bundle().apply { putSerializable("product", it)}
            findNavController().navigate(R.id.detailsFragment, bundle)
        }
    }

    private fun performSearch(query: String?) {
        searchViewModel.getSearchProducts(query!!).observe(viewLifecycleOwner){product ->
            productAdapter.differ.submitList(product.products)
            binding.rvSearchId.layoutManager = GridLayoutManager(context, 2)
            binding.rvSearchId.adapter = productAdapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable!!)
    }


}