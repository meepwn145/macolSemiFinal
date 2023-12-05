package com.macol_richard.animal.tweetmacol

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface ApiService {
    @POST("tweet/macol/")
    fun postTweet(@Body tweetData: TweetData): Call<Void>

    @DELETE("tweet/macol/{tweetName}/")
    fun deleteTweet(@Path("tweetName") tweetName: String): Call<Void>

    @PUT("tweet/macol/{name}")
    fun updateTweet(@Path("name") name: String, @Body updatedTweet: TweetData): Call<Void>
}
