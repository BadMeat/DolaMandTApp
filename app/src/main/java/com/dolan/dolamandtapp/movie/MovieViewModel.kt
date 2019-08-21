package com.dolan.dolamandtapp.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MovieViewModel : ViewModel() {

    private val itemMovie: MutableLiveData<MutableList<MovieResponse>> = MutableLiveData()

    companion object {
        const val API_KEY = "3da8902babab85c9ac30e837198e9bf9"
    }

    fun setMovie() {
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY&language=en-US"
        val listItem: MutableList<MovieResponse> = mutableListOf()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                try {
                    val respondObject = String(responseBody!!)
                    val respondArray = JSONObject(respondObject)
                    val list = respondArray.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItem = MovieResponse(movie)
                        movieItem.poster = "http://image.tmdb.org/t/p/w185${movieItem.poster}"
                        listItem.add(movieItem)
                    }
                    itemMovie.postValue(listItem)
                } catch (e: Exception) {
                    Log.e("Error", e.message)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e("Error", error?.message)
            }

        })
    }

    fun getItem(): LiveData<MutableList<MovieResponse>> {
        return itemMovie
    }

}