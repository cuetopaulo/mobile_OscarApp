package br.ufpr.oscarapp.telasDiretores

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiDiretoresClient {
    private const val BASE_URL = "http://wecodecorp.com.br/"

    val api: DiretoresApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(DiretoresApi::class.java)

    }
}