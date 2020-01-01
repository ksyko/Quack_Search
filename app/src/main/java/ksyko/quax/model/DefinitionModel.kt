package ksyko.quax.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DefinitionModel(
    @Json(name = "meaning")
    val meaning: Meaning,
    @Json(name = "origin")
    val origin: String,
    @Json(name = "phonetic")
    val phonetic: String,
    @Json(name = "word")
    val word: String
)