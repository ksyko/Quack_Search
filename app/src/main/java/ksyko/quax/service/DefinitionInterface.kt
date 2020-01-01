package ksyko.quax.service

import ksyko.quax.model.DefinitionModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DefinitionInterface {
    @GET("/?")
    suspend fun getDefinition(
        @Query("define") define: String,
        @Query("lang") lang: String
    ): Response<List<DefinitionModel>>
}