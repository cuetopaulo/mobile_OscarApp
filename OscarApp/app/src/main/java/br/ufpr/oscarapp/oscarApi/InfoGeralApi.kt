package br.ufpr.oscarapp.oscarApi

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InfoGeralApi {
    @GET("info/{login}")
    suspend fun getLogin(@Path("login") login: String): InfoGeral
    @GET("info/{senha}")
    suspend fun getSenha(@Path("senha") senha: String): InfoGeral
    @GET("info/{token}")
    suspend fun getToken(@Path("token") token: String): InfoGeral
    @GET("info/{votoFilme}")
    suspend fun getVotoFilme(@Path("votoFilme") votoFilme: String): InfoGeral
    @GET("info/{votoDiretor}")
    suspend fun getVotoDiretor(@Path("votoDiretor") votoDiretor: String): InfoGeral

    @POST("info")
    suspend fun postInfo(info: InfoGeral): InfoGeral
    // continuar



}