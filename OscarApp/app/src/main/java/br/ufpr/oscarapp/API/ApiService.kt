package br.ufpr.oscarapp.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Interface para os endpoints da API
interface ApiService {

    // Endpoint para login
    @POST("api/login/")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

//    @POST("login/")
//    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // Endpoint para registro de usuário
    @POST("api/register/")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}