package br.ufpr.oscarapp.API


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUnico {

    // Base URL da sua API Django
    private const val BASE_URL = "http://192.168.0.8:8000/" // Altere para o IP do seu servidor Django

    // Criação do Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Usando o Gson para converter JSON em objetos
        .build()

    // Criação da instância da ApiService
    val api = retrofit.create(ApiService::class.java)
}