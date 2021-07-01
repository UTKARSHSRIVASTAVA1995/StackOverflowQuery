package com.devtides.stackoverflowquery.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StaclOverflowApi {

    @GET("/2.3/questions?order=desc&sort=votes&tagged=android&site=stackoverflow")
    fun getQuestions(@Query("page") page: Int): retrofit2.Call<ResponseWrapper<Questions>>

    @GET("/2.3/questions/{id}/answers?order=desc&sort=votes&site=stackoverflow")
    fun getAnswers(
        @Path("id") questionId: Int,
        @Query("page") page: Int
    ): Call<ResponseWrapper<Answers>>
}