package com.guardiannews.android.testnewsapp.framework.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiResponse (
        @Json(name ="response")
        var response: Response? = null

)
