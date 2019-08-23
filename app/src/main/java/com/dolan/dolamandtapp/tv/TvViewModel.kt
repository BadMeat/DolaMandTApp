package com.dolan.dolamandtapp.tv

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

/**
 * Created by Bencoleng on 20/08/2019.
 */
class TvViewModel : ViewModel() {

    private var listTv: MutableLiveData<MutableList<TvResponse>> = MutableLiveData()

    fun setTv(defaultDesc : String) {
        val client = AsyncHttpClient()
        val listItem: MutableList<TvResponse> = mutableListOf()
        var language = "en-US"
        if (Locale.getDefault().displayCountry.equals("Indonesia", true)) {
            language = "id"
        }

        val url =
            "${BuildConfig.BASE_URL}tv/popular?api_key=${BuildConfig.API_KEY}&language=$language"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody!!)
                    val respondObject = JSONObject(result)
                    val list = respondObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val tv = list.getJSONObject(i)
                        val tvItem = TvResponse(tv)
                        if(tvItem.desc.isEmpty()){
                            tvItem.desc = defaultDesc
                        }
                        tvItem.posterPath = "${BuildConfig.IMAGE_URL}${tvItem.posterPath}"
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