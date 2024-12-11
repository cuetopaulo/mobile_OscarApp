package br.ufpr.oscarapp.oscarApi

data class InfoGeral(
    val login: String,
    val senha: String,
    val token: String,
    val votoFilme: String,
    val votoDiretor: String
)
