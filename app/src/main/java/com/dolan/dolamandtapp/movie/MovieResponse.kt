package com.dolan.dolamandtapp.movie

import org.json.JSONObject

data class MovieResponse(
    val jsonObject: JSONObject
) {
    val title: String = jsonObject.getString("title")
    var desc : String = jsonObject.getString("overview")
    val rate: Double = jsonObject.getDouble("vote_average")
    var poster: String = jsonObject.getString("poster_path")
}