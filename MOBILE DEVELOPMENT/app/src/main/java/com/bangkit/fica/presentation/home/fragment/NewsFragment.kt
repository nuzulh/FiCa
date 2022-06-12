package com.bangkit.fica.presentation.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.fica.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    private var _binding                    : FragmentNewsBinding?      = null
    private val binding get()                                           = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initClickListener(){
        with(binding){

        }
    }
}