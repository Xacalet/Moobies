package com.xacalet.moobies.net.api.dto

import com.squareup.moshi.Json

data class SpokenLanguageDto(
    @field:Json(name = "iso_639_1")
    val iso6391: String? = null,
    @field:Json(name = "name")
    val name: String? = null
)
