package ksyko.quax.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meaning(
    @Json(name = "intransitive verb")
    val intransitiveVerb: List<IntransitiveVerb>,
    @Json(name = "exclamation")
    val exclamation: List<Exclamation>,
    @Json(name = "noun")
    val noun: List<Noun>
)