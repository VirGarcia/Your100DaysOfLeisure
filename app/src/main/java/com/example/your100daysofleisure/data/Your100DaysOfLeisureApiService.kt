package com.example.your100daysofleisure.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Your100DaysOfLeisureApiService {
    @GET("206974-0-agenda-eventos-culturales-100.json")
    suspend fun findLeisureByDistrict(@Query("distrito_nombre") district: String): Your100DaysOfLeisureResponse

    @GET("206974-0-agenda-eventos-culturales-100.json")
    suspend fun findAllLeisures(): Your100DaysOfLeisureResponse


}
