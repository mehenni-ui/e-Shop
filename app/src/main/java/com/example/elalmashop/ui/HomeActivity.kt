package com.example.elalmashop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elalmashop.R
import com.example.elalmashop.databinding.ActivityHomeBinding
import com.example.elalmashop.ui.adapters.CartAdapter
import com.example.elalmashop.ui.fragments.CartFragment
import com.stripe.android.paymentsheet.PaymentSheet

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var paymentSheet: PaymentSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.homeFragmentContainer) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNavigationId.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination.id == R.id.detailsFragment){
                binding.bottomNavigationId.visibility = View.GONE
            }else{
                binding.bottomNavigationId.visibility = View.VISIBLE
            }
        }




    }

    fun updateCartItemNumber(cartItemNumber: Int) {
        binding.bottomNavigationId.getOrCreateBadge(R.id.cartFragment).number = cartItemNumber
    }


}