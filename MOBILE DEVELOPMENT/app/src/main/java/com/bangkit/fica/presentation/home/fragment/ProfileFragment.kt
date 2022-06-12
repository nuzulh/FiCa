package com.bangkit.fica.presentation.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.fica.databinding.FragmentProfileBinding
import com.bangkit.fica.utils.extension.goToLogin
import com.bangkit.fica.utils.preferences.AuthPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding                : FragmentProfileBinding?   = null
    private val binding get()                                       = _binding!!
    private lateinit var auth           : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        with(binding){
            tvEmail.text = AuthPrefs.email
        }
        initClickListener()
    }

    private fun initClickListener(){
        with(binding){
            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                AuthPrefs.hadLogin   = false
                AuthPrefs.email      = ""
                requireActivity().goToLogin()
                requireActivity().finish()
            }
        }
    }
}