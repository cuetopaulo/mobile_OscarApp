package br.ufpr.oscarapp.telasFilmes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFilmesClient {
    private const val BASE_URL = "http://wecodecorp.com.br/"

    val api: FilmesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmesApi::class.java)
    }
}
