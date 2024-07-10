package com.example.your100daysofleisure.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Your100DaysOfLeisureApiService {
    @GET("206974-0-agenda-eventos-culturales-100.json")
    suspend fun findAllLeisures(): Your100DaysOfLeisureResponse

    @GET
    suspend fun findLeisureById(@Url url: String): Your100DaysOfLeisureResponse



}
