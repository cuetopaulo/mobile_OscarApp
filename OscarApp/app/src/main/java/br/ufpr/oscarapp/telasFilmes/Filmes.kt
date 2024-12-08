package br.ufpr.oscarapp.telasFilmes

data class Filmes(
    val id: String,
    val nome: String,
    val genero: String,
    val foto: Int // Referência ao recurso drawable
)
