package br.ufpr.oscarapp.telasFilmes

import retrofit2.http.GET

interface FilmesApi {
    @GET("ufpr/filme")
    suspend fun getFilmes(): List<Filme>
}
