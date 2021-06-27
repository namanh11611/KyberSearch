package com.namanh.kybersearch.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.namanh.kybersearch.viewmodel.SharedViewModel

abstract class BaseFragment<BD: ViewBinding> : Fragment() {

    val mSharedViewModel: SharedViewModel by activityViewModels()
    var _binding: BD? = null
    val binding: BD
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBindingInflate(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun viewBindingInflate(inflater: LayoutInflater, container: ViewGroup?): BD
}