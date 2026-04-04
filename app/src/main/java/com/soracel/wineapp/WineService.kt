package com.soracel.wineapp

import retrofit2.http.GET

interface WineService {

    @GET(Constants.PATH_WINES)
    suspend fun getWines(): List<Wine>
}