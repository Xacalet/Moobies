package com.xacalet.moobies.net.api.dto

import com.squareup.moshi.Json

data class ProductionCountryDto(
    @field:Json(name = "iso_3166_1")
    val iso31661: String? = null,
    @field:Json(name = "name")
    val name: String? = null
)
