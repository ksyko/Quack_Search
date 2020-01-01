package ksyko.quax.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Noun(
    @Json(name = "definition")
    val definition: String,
    @Json(name = "example")
    val example: String,
    @field:Json(name = "synonyms")
    val synonyms: List<String>
)