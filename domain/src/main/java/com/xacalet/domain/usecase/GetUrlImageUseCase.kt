package com.xacalet.domain.usecase

import com.xacalet.domain.model.Configuration
import com.xacalet.domain.model.Images
import javax.inject.Inject

class GetImageUrlUseCase @Inject constructor() {

    // TODO: Get this configuration from backend
    private var configuration: Configuration = Configuration(
        emptyList(),
        Images(
            baseUrl = "http://image.tmdb.org/t/p/",
            secureBaseUrl = "https://image.tmdb.org/t/p/",
            backdropSizes = listOf("w300", "w780", "w1280", "original"),
            logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
            posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
            profileSizes = listOf("w45", "w185", "h632", "original"),
            stillSizes = listOf("w92", "w185", "w300", "original")
        )
    )

    operator fun invoke(imageWidth: Int, filePath: String): String =
        configuration.images.let { images ->
            val baseUrl = images.secureBaseUrl
            val widthMap = images.posterSizes.map { size ->
                if (size == "original") {
                    Int.MAX_VALUE
                } else {
                    size.removePrefix("w").toInt()
                } to size
            }.sortedBy { it.first }.toMap()
            val size = widthMap.keys.first { width -> width > imageWidth }.let { key ->
                widthMap[key]
            }
            "$baseUrl$size/$filePath"
        }
}
