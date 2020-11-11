package com.guardiannews.android.testnewsapp.framework.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response (
        @Json(name = "status")

        var status: String? = null,

        @Json(name = "userTier")

        var userTier: String? = null,

        @Json(name = "total")

        var total: Int? = null,

        @Json(name = "startIndex")

        var startIndex: Int? = null,

        @Json(name = "pageSize")

        var pageSize: Int? = null,

        @Json(name = "currentPage")

        var currentPage: Int? = null,

        @Json(name = "pages")

        var pages: Int? = null,

        @Json(name = "orderBy")

        var orderBy: String? = null,

        @Json(name = "results")

        var results: List<Result>? = null

)
