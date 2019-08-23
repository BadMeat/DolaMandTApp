package com.dolan.dolamandtapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class DetailViewModel : ViewModel() {
    private var data: MutableLiveData<DetailResponse> = MutableLiveData()

    fun setData(id: Int, type: Int) {
        try {
            Log.d("Id", id.toString())
            val client = AsyncHttpClient()
            var language = "en-US"
            if (Locale.getDefault().displayCountry.equals("Indonesia", true)) {
                language = "id"
            }
            var kind = ""
            if (type == 1) {
                kind = "movie"
            } else if (type == 2) {
                kind = "tv"
            }
            val url =
                "${BuildConfig.BASE_URL}$kind/$id?api_key=${BuildConfig.API_KEY}&language=$language"
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
                ) {
                    val result = String(responseBody!!)
                    val responds = JSONObject(result)
                    var title = ""
                    if (type == 1) {
                        title = responds.getString("original_title")
                    } else if (type == 2) {
                        title = responds.getString("name")
                    }
                    val desc = responds.getString("overview")
                    val rate = responds.getDouble("vote_average")
                    val poster = responds.getString("poster_path")
                    val item =
                        DetailResponse(title, desc, rate, "${BuildConfig.IMAGE_URL}$poster")
                    data.postValue(item)
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
        } catch (e: Exception) {
            print(e.message)
        }
    }

    fun getData(): MutableLiveData<DetailResponse> {
        return data
    }
}