package br.ufpr.oscarapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val login: String, val senha: String)
data class LoginResponse(val token: Int)
data class VoteRequest(val token: Int, val filme: String, val diretor: String)
data class VoteResponse(val mensagem: String)
data class CheckVoteRequest(val login: String)
data class CheckVoteResponse(val voted: Boolean)
data class GetVoteDetailsRequest(val login: String)
data class GetVoteDetailsResponse(
    val filme: String,
    val diretor: String,
    val timestamp: String
)

interface PostApi {
    @POST("login")
    suspend fun login(@Body credentials: LoginRequest): Response<LoginResponse>

    @POST("voto")
    suspend fun submitVote(@Body vote: VoteRequest): Response<VoteResponse>

    @POST("checa_voto")
    suspend fun checkVote(@Body request: CheckVoteRequest): Response<CheckVoteResponse>

    @POST("pega_voto_detalhes")
    suspend fun getVoteDetails(@Body request: GetVoteDetailsRequest): Response<GetVoteDetailsResponse>
}

