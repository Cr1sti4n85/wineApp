package com.soracel.wineapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.soracel.wineapp.databinding.FragmentHomeBinding
import kotlin.collections.listOf

open class BaseFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    protected fun showRecyclerView(isVisible: Boolean){
        binding.recyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    protected fun showNoData(isVisible: Boolean){
        binding.tvNoData.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    protected fun showProgress(isVisible: Boolean){
        binding.srlWines.isRefreshing = isVisible
    }

    protected fun showMsg(msgRes: Int){
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}