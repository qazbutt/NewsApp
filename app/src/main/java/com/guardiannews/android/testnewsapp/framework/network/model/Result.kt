package com.guardiannews.android.testnewsapp.framework.network.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Result (
        @Json(name = "id")

        var id: String? = null,

        @Json(name = "type")

        var type: String? = null,

        @Json(name = "sectionId")

        var sectionId: String? = null,

        @Json(name = "sectionName")

        var sectionName: String? = null,

        @Json(name = "webPublicationDate")

        var webPublicationDate: String? = null,

        @Json(name = "webTitle")

        var webTitle: String? = null,

        @Json(name = "webUrl")

        var webUrl: String? = null,

        @Json(name = "apiUrl")

        var apiUrl: String? = null,

        @Json(name = "isHosted")

        var isHosted: Boolean? = null,

        @Json(name ="fields")
        var  fields:Fields ?=null,

        @Json(name = "pillarId")

        var pillarId: String? = null,

        @Json(name = "pillarName")

        var pillarName: String? = null

)
