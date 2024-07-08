package com.example.your100daysofleisure.data

import retrofit2.http.GET
import retrofit2.http.Path

interface Your100DaysOfLeisureApiService {
    @GET("search/{name}")
    suspend fun findLeisureByName(@Path("distrito_nombre")query: String) : Your100DaysOfLeisureResponse

    @GET("{}")
    suspend fun findAllLeisure(@Path("") id: Int) : Leisure

}