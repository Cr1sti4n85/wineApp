package com.soracel.wineapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wines")
data class Wine(
    val winery: String,
    val wine: String,
    val rating: Rating,
    val location: String,
    val image: String,
    @PrimaryKey val id: Int,
    var isFavorite: Boolean = false
)
