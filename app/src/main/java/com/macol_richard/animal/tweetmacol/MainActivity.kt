package com.macol_richard.animal.tweetmacol

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextTweet: EditText
    private lateinit var btnPostTweet: Button
    private lateinit var apiService: ApiService
    private lateinit var btnDeleteTweet: Button
    private lateinit var btnUpdateTweet: Button


    private fun clearEditTexts() {
        editTextName.text.clear()
        editTextTweet.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextTweet = findViewById(R.id.editTextTweet)
        btnPostTweet = findViewById(R.id.btnPostTweet)
        btnDeleteTweet = findViewById(R.id.btnDeleteTweet)
        btnUpdateTweet = findViewById(R.id.btnUpdateTweet)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://eldroid.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)


        btnPostTweet.setOnClickListener {
            val name = editTextName.text.toString()
            val tweet = editTextTweet.text.toString()

            val tweetData = TweetData(name, tweet)

            Log.d("POST_TWEET", "Sending request with data: $tweetData")

            apiService.postTweet(tweetData).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {

                        clearEditTexts()

                        Log.d("POST_TWEET", "Success")
                    } else {
                        Log.e(
                            "POST_TWEET",
                            "Error: ${response.code()}, ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("POST_TWEET", "Failure: ${t.message}")
                }
            })
             fun deleteTweet(tweet: TweetData, position: Int) {
                val tweetName = tweet.name
                apiService.deleteTweet(tweetName).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {

                            Log.d("DELETE_TWEET", "Success")
                        } else {
                            Log.e("DELETE_TWEET", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("DELETE_TWEET", "Failure: ${t.message}")
                    }
                })
                  fun updateTweet(updatedTweet: TweetData, position: Int) {
                     val tweetName = updatedTweet.name
                     apiService.updateTweet(tweetName, updatedTweet).enqueue(object : Callback<Void> {
                         override fun onResponse(call: Call<Void>, response: Response<Void>) {
                             if (response.isSuccessful) {

                                 clearEditTexts()
                                 Log.d("UPDATE_TWEET", "Success")
                             } else {
                                 Log.e("UPDATE_TWEET", "Error: ${response.code()}")
                             }
                         }

                         override fun onFailure(call: Call<Void>, t: Throwable) {
                             Log.e("UPDATE_TWEET", "Failure: ${t.message}")
                         }
            })}
        }
    }
}}