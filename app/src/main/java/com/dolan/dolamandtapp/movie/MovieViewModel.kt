package com.dolan.dolamandtapp.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolamandtapp.BuildConfig
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class MovieViewModel : ViewModel() {

    private val itemMovie: MutableLiveData<MutableList<MovieResponse>> = MutableLiveData()

    fun setMovie(defaultDesc: String) {
        val client = AsyncHttpClient()
        var language = "en-US"
        if (Locale.getDefault().displayCountry.equals("Indonesia", true)) {
            language = "id"
        }
        val url =
            "${BuildConfig.BASE_URL}movie/popular?api_key=${BuildConfig.API_KEY}&language=$language"
        val listItem: MutableList<MovieResponse> = mutableListOf()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val respondObject = String(responseBody!!)
                    val respondArray = JSONObject(respondObject)
                    val list = respondArray.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItem = MovieResponse(movie)
                        if (movieItem.desc.isEmpty()) {
                            movieItem.desc = defaultDesc
                        }
                        movieItem.poster = "${BuildConfig.IMAGE_URL}${movieItem.poster}"
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