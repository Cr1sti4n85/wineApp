package com.soracel.wineapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WineDao {
    @Query("SELECT * FROM wines")
    fun getAllWines(): MutableList<Wine>

    @Insert
    fun addWine(wine: Wine): Long
}