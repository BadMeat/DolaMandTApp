package com.dolan.dolamandtapp.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Created by Bencoleng on 20/08/2019.
 */
class TvViewModel : ViewModel() {

    private var listTv: MutableLiveData<MutableList<TvResponse>> = MutableLiveData()

    companion object {
        const val API_KEY = "3da8902babab85c9ac30e837198e9bf9"
    }

    fun setTv() {
        val client = AsyncHttpClient()
        val listItem: MutableList<TvResponse> = mutableListOf()
        val url = "https://api.themoviedb.org/3/tv/popular?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                try {
                    val result = String(responseBody!!)
                    val respondObject = JSONObject(result)
                    val list = respondObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val tv = list.getJSONObject(i)
                        val tvItem = TvResponse(tv)
                        tvItem.posterPath = "http://image.tmdb.org/t/p/w185${tvItem.posterPath}"
                        listItem.add(tvItem)
                    }
                    listTv.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("Error", e.message)
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

    fun getTv(): LiveData<MutableList<TvResponse>> {
        return listTv
    }

}