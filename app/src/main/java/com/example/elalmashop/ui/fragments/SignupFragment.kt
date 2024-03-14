package com.example.elalmashop.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.elalmashop.R
import com.example.elalmashop.databinding.FragmentSignupBinding
import com.example.elalmashop.ui.utils.Resource
import com.example.elalmashop.ui.viewModels.RegisterViewModel

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)


        binding.btnGoToLogIn.setOnClickListener {
            it.findNavController().navigate(R.id.loginFragment)
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailSignUp.text.toString()
            val password = binding.etPasswordSignUp.text.toString()
            val confirmPassword = binding.etConfirmPasswordSignUp.text.toString()

            registerViewModel.observeSignUp(email, password, confirmPassword).observe(viewLifecycleOwner
            ) { result ->
                when (result) {
                    is Resource.Success<*> -> {
                        Toast.makeText(context, "USER SIGNUP SUCCESSFULLY", Toast.LENGTH_LONG)
                            .show()
                    }

                    is Resource.Error<*> -> {
                        result.let {
                            Toast.makeText(
                                context,
                                "something went wrong signup",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    else -> {}
                }
            }
        }
    }




}