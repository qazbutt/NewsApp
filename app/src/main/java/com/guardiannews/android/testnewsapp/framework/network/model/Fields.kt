package com.guardiannews.android.testnewsapp.framework.network.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Fields (

        @Json(name = "trailText")
        var trailText:String?=null,
        @Json(name = "thumbnail")
        var thumbnail:String?=null
)

