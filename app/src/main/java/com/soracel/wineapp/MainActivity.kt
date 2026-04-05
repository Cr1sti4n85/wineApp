package com.soracel.wineapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.soracel.wineapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var adapter: WineListAdapter
    private lateinit var binding: ActivityMainBinding

    private lateinit var service: WineService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAdapter()
        setupRecyclerView()
        setupRetrofit()
        setupSwipeRefresh()
    }

    private fun setupAdapter() {
        adapter = WineListAdapter()
        adapter.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupSwipeRefresh(){
        binding.srlWines.setOnRefreshListener {
            adapter.submitList(listOf()) //limpia la lista
            getWines()
        }
    }

    private fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(WineService::class.java)
    }

    private fun getWines() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val serverOK = Random.nextBoolean()
                val wines = if(serverOK) service.getWines() else emptyList()

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

    private fun showRecyclerView(isVisible: Boolean){
        binding.recyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showNoData(isVisible: Boolean){
        binding.tvNoData.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showProgress(isVisible: Boolean){
        binding.srlWines.isRefreshing = isVisible
    }

    private fun showMsg(msgRes: Int){
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        showProgress(true)
        getWines()
    }

    /*OnClickListener implementation*/
    override fun onLongClick(wine: Wine) {
        val options = resources.getStringArray(R.array.array_dialog_add_options)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_add_fav_title)
            .setItems(options, ){_, index ->
                when(index){
                    0 -> addToFavorites(wine)
                }
            }
            .show()
    }

    private fun addToFavorites(wine: Wine) {
        showMsg(R.string.room_save_success)
    }
}