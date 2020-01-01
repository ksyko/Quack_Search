package ksyko.quax.service

import ksyko.quax.model.DuckResultsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckInterface {
    @GET("/?format=json&no_redirect=0")
    suspend fun getResults(@Query("q") query: String): Response<List<DuckResultsModel>>
}