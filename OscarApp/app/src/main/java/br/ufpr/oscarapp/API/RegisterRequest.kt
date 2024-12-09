package br.ufpr.oscarapp.API

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String
)
