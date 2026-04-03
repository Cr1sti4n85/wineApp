package com.soracel.wineapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soracel.wineapp.databinding.ItemWineBinding

class WineListAdapter: ListAdapter<Wine, RecyclerView.ViewHolder>(WineDiff()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wine, parent, false))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val wine = getItem(position)
        (holder as ViewHolder).run {
            with(binding) {
                tvWine.text = wine.wine
                tvWinery.text = wine.winery
                tvLocation.text = wine.location
                rating.rating = wine.rating.average.toFloat()
            }
        }

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemWineBinding.bind(view)
    }

    private class WineDiff: DiffUtil.ItemCallback<Wine>(){
        override fun areItemsTheSame(
            oldItem: Wine,
            newItem: Wine
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Wine,
            newItem: Wine
        ): Boolean {
            return oldItem == newItem
        }

    }
}