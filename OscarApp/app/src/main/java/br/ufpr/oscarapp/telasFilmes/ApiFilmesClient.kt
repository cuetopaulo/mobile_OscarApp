package br.ufpr.oscarapp.telasFilmes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFilmesClient {
    private const val BASE_URL = "http://wecodecorp.com.br/"

    val api: FilmesApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(FilmesApi::class.java)
    }
}
