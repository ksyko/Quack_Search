package ksyko.quax.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DuckResultsModel(
    @Json(name = "Abstract")
    val `abstract`: String,
    @Json(name = "AbstractSource")
    val abstractSource: String,
    @Json(name = "AbstractText")
    val abstractText: String,
    @Json(name = "AbstractURL")
    val abstractURL: String,
    @Json(name = "Answer")
    val answer: String,
    @Json(name = "AnswerType")
    val answerType: String,
    @Json(name = "Definition")
    val definition: String,
    @Json(name = "DefinitionSource")
    val definitionSource: String,
    @Json(name = "DefinitionURL")
    val definitionURL: String,
    @Json(name = "Entity")
    val entity: String,
    @Json(name = "Heading")
    val heading: String,
    @Json(name = "Image")
    val image: String,
    @Json(name = "ImageHeight")
    val imageHeight: String,
    @Json(name = "ImageIsLogo")
    val imageIsLogo: String,
    @Json(name = "ImageWidth")
    val imageWidth: String,
    @Json(name = "Infobox")
    val infobox: String,
    @Json(name = "meta")
    val meta: Any,
    @Json(name = "RelatedTopics")
    val relatedTopics: List<Any>,
    @Json(name = "Results")
    val results: List<Any>,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Redirect")
    val redirect: String
)