package com.soracel.wineapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soracel.wineapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WineListAdapter
    private lateinit var binding: ActivityMainBinding

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
    }

    private fun setupAdapter() {
        adapter = WineListAdapter()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            adapter = this@MainActivity.adapter
        }
    }

    private fun getWines() {
        val wines = getLocalWines()
        adapter.submitList(wines)
    }

    private fun getLocalWines() = listOf(Wine(
        "Maselva",
        "Emporda 2012",
        Rating("4.8", "540 ratings"),
        "France",
        "https://images.vivino.com/thumbs/nC9V6L2mQQSq0s-wZLcaxw_pb_x300.png",
        1
    ), Wine(
        "Ernesto Ruffo",
        "Carmenere",
        Rating("4.8", "540 ratings"),
        "Italy",
        "https://images.vivino.com/thumbs/00o0kud1xybsc_375x500.jpg",
        1
    ), Wine(
        "Cartuxa",
        "The Beast Cabernet Sauvignon 2012",
        Rating("4.8", "540 ratings"),
        "Spain",
        "https://images.vivino.com/thumbs/nC9V6L2mQQSq0s-wZLcaxw_pb_x300.png",
        1
    ),
        Wine(
            "Sine Qua Non",
            "Merlot",
            Rating("4.8", "540 ratings"),
            "Chile",
            "https://images.vivino.com/thumbs/00o0kud1xybsc_375x500.jpg",
            1
        ),
        Wine(
            "Garbole",
            "Hurlo 2009",
            Rating("4.8", "540 ratings"),
            "France",
            "https://images.vivino.com/thumbs/nC9V6L2mQQSq0s-wZLcaxw_pb_x300.png",
            1
        )

    )

    override fun onResume() {
        super.onResume()
        getWines()
    }
}