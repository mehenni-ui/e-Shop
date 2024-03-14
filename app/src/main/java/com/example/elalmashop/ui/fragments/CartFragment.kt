package com.example.elalmashop.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elalmashop.R
import com.example.elalmashop.ui.adapters.CartAdapter
import com.example.elalmashop.databinding.FragmentCartBinding
import com.example.elalmashop.data.db.local.ProductDb
import com.example.elalmashop.data.repo.ProductRepo
import com.example.elalmashop.ui.HomeActivity
import com.example.elalmashop.ui.viewModels.LocalDbViewModel
import com.example.elalmashop.ui.viewModels.LocalDbViewModelFactory

import com.google.android.material.snackbar.Snackbar

import com.stripe.android.paymentsheet.PaymentSheet

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var localDbViewModel: LocalDbViewModel
    private lateinit var localDbViewModelFactory: LocalDbViewModelFactory
    private lateinit var cartAdapter: CartAdapter
    private lateinit var paymentSheet: PaymentSheet


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)


        val productRepo = ProductRepo(ProductDb(requireContext()))
        localDbViewModelFactory = LocalDbViewModelFactory(productRepo)

        localDbViewModel = ViewModelProvider(this, localDbViewModelFactory)[LocalDbViewModel::class.java]
        cartAdapter = CartAdapter(requireContext())





        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)



        binding.rvCartId.adapter = cartAdapter
        binding.rvCartId.layoutManager = LinearLayoutManager(context)

        localDbViewModel.getAllProduct().observe(viewLifecycleOwner){
            cartAdapter.differ.submitList(it)
            (requireActivity() as HomeActivity).updateCartItemNumber(it.size)
        }

        cartAdapter.onItemClick = {
            val bundle = Bundle().apply { putSerializable("product", it) }
            findNavController().navigate(R.id.detailsFragment, bundle)
        }


        localDbViewModel.getTotalPrice().observe(viewLifecycleOwner){
            binding.txtTotalPrice.text  = "${it.toString()}$"
        }

        cartAdapter.onIncreaseProductNumberClickListener= {
            localDbViewModel.increaseProductNumber(it)
        }

        cartAdapter.onDecreaseProductNumberClickListener = {
            localDbViewModel.decreaseProductNumber(it)
        }

        val itemTouchHelper  = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product = cartAdapter.differ.currentList[position]
                localDbViewModel.deleteProduct(product)

                Snackbar.make(view, "Item deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        localDbViewModel.insertProduct(product)
                    }
                    show()
                }
            }






        })

        itemTouchHelper.attachToRecyclerView(binding.rvCartId)











    }






}