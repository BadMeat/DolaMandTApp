package com.dolan.dolamandtapp.tv

import org.json.JSONObject

/**
 * Created by Bencoleng on 20/08/2019.
 */
data class TvResponse(
    val jsonObject: JSONObject
) {
    val id: Int = jsonObject.getInt("id")
    val name: String = jsonObject.getString("name")
    val rate: Double = jsonObject.getDouble("vote_average")
    var posterPath: String = jsonObject.getString("poster_path")
    var desc: String = jsonObject.getString("overview")
}