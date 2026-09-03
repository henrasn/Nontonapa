package com.henrasn.nontonapa.data.model.dto.genre

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreResponse(

    @SerialName("genres")
    val genres: List<GenresItem?>? = null
)

@Serializable
data class GenresItem(

    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: Int
)
