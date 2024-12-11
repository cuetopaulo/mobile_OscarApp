package br.ufpr.oscarapp.telasDiretores

import retrofit2.http.GET

interface DiretoresApi {
    @GET("ufpr/diretor")
    suspend fun getDiretores(): List<Diretor>
}