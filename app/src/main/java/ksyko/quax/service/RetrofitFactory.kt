package ksyko.quax.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {
    internal const val dictionaryApi = "https://mydictionaryapi.appspot.com"
    internal const val duckBangApi = "https://api.duckduckgo.com"

    fun makeDefinitionService(): DefinitionInterface {
        return Retrofit.Builder()
            .baseUrl(dictionaryApi)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(DefinitionInterface::class.java)
    }

    fun makeDuckService(): DuckInterface {
        return Retrofit.Builder()
            .baseUrl(duckBangApi)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build().create(DuckInterface::class.java)
    }

    fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


}