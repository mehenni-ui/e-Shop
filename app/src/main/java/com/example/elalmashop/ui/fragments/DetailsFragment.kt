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
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.elalmashop.ui.adapters.ProductViewPagerAdapter
import com.example.elalmashop.databinding.FragmentDetailsBinding
import com.example.elalmashop.data.db.local.ProductDb
import com.example.elalmashop.data.model.Product
import com.example.elalmashop.data.repo.ProductRepo
import com.example.elalmashop.ui.viewModels.FeedViewModel
import com.example.elalmashop.ui.viewModels.LocalDbViewModel
import com.example.elalmashop.ui.viewModels.LocalDbViewModelFactory


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val detailArgs: DetailsFragmentArgs by navArgs()
    private lateinit var product: Product
    private val feedViewModel: FeedViewModel  by viewModels()
    private lateinit var localDbViewModel: LocalDbViewModel
    private lateinit var localDbViewModelFactory: LocalDbViewModelFactory
    private var productNumber: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        product = detailArgs.product

        val productRepo = ProductRepo(ProductDb(requireContext()))
        localDbViewModelFactory = LocalDbViewModelFactory(productRepo)

        localDbViewModel = ViewModelProvider(this, localDbViewModelFactory).get(LocalDbViewModel::class.java)
        productNumber = product.productNumber

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        Log.d("product_deta", product.toString())

        binding.viewPagerId.adapter = ProductViewPagerAdapter(requireContext(), product.images)
        binding.viewPagerId.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.txtTitleDetails.text = product.title
        binding.txtBrandDetails.text = product.brand
        binding.ratingBarDetails.rating = 5F



        binding.txtPriceDetails.text = "${product.price}$"

        binding.txtDescriptionDetails.text = product.description

        feedViewModel.getProductNumber().observe(viewLifecycleOwner){
            productNumber = it
            binding.productNumberId.text = it.toString()
        }

        binding.btnAddProductNumberId.setOnClickListener {
            feedViewModel.increaseProductNumber(product.stock).observe(viewLifecycleOwner){
                productNumber = it
                binding.productNumberId.text = it.toString()
            }
        }

        binding.btnMinusProductNumberId.setOnClickListener {
            feedViewModel.decreaseProductNumber().observe(viewLifecycleOwner){
                productNumber = it
                binding.productNumberId.text = it.toString()
            }
        }

        binding.btnAddToCart.setOnClickListener {

            product.productNumber = productNumber
            localDbViewModel.insertProduct(product)

            Toast.makeText(context, "product has been add to your cart", Toast.LENGTH_LONG).show()



            localDbViewModel.getAllProduct().observe(viewLifecycleOwner){
                Log.d("cart_list", it.toString())
            }

        }


    }

}