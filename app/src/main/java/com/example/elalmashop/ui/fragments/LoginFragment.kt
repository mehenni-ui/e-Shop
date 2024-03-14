package com.example.elalmashop.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.elalmashop.R
import com.example.elalmashop.databinding.FragmentLoginBinding
import com.example.elalmashop.ui.HomeActivity
import com.example.elalmashop.ui.utils.Resource
import com.example.elalmashop.ui.viewModels.RegisterViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)


        binding.btnGoToSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.signupFragment)
        }

        binding.btnLogIn.setOnClickListener {
            val email = binding.etEmailLogIn.text.toString()
            val password = binding.etPasswordLogIn.text.toString()

            registerViewModel.observeLogin(email, password).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Success<*> -> {
                        Toast.makeText(context, "USER SIGNUP SUCCESSFULLY", Toast.LENGTH_LONG)
                            .show()
                        startActivity(Intent(context, HomeActivity::class.java))
                    }

                    is Resource.Error<*> -> {
                        Toast.makeText(context, "something went wrong login", Toast.LENGTH_LONG)
                            .show()
                    }

                    else -> {}
                }
            }

        }



    }






    }


