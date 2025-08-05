package com.example.cft_test_task.api

import com.example.cft_test_task.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApiService {

    @GET("api/")
    fun getUsers(
        @Query("results") results: Int
    ): Call<UsersResponse>
}