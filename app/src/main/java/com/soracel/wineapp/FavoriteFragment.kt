package com.soracel.wineapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class FavoriteFragment: BaseFragment(), OnClickListener {
    private lateinit var adapter: WineListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupAdapter() {
        adapter = WineListAdapter()
        adapter.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteFragment.adapter
        }
    }

    private fun setupSwipeRefresh(){
        binding.srlWines.setOnRefreshListener {
            adapter.submitList(listOf()) //limpia la lista
            getWines()
        }
    }

    private fun getWines() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {

                val wines = WineApplication.database.wineDao().getAllWines()

                withContext(Dispatchers.Main){
                    if(wines.isNotEmpty()){
                        showNoData(false)
                        showRecyclerView(true)
                        adapter.submitList(wines)
                    } else {
                        showRecyclerView(false)
                        showNoData(true)
                    }
                }
            } catch (e: Exception){
                showMsg(R.string.common_string_failed)
            } finally {
                showProgress(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showProgress(true)
        getWines()
    }

    /*OnClickListener implementation*/
    override fun onLongClick(wine: Wine) {}

    override fun onFavorite(wine: Wine) {
        TODO("Not yet implemented")
    }
}