package br.ufpr.oscarapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TelaConfirmarVoto : AppCompatActivity() {
    private var votesSubmitted = false // Tracks if the votes have been submitted

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_confirmar_voto)

        // pega votos
        val sharedPreferences = getSharedPreferences("UserVotes", Context.MODE_PRIVATE)
        val selectedMovie = sharedPreferences.getString("selectedMovie", "Filme não selecionado")
        val selectedDirector = sharedPreferences.getString("selectedDirector", "Diretor não selecionado")

        // seta nos textviews
        val tvFilmeEscolhido = findViewById<TextView>(R.id.tvFilmeEscolhido)
        val tvDiretorEscolhido = findViewById<TextView>(R.id.tvDiretorEscolhido)
        val etToken = findViewById<EditText>(R.id.tvTokenInserir) // Token input field
        val btnSubmit = findViewById<Button>(R.id.btSalvarVotoFinalizar)

        // mostra votos
        tvFilmeEscolhido.text = selectedMovie
        tvDiretorEscolhido.text = selectedDirector

        // enviar votos
        btnSubmit.setOnClickListener {
            if (votesSubmitted) {
                Toast.makeText(this, "Você já enviou seus votos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tokenString = etToken.text.toString()
            if (tokenString.isEmpty()) {
                Toast.makeText(this, "Por favor, insira o token.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val token = tokenString.toIntOrNull()
            if (token == null) {
                Toast.makeText(this, "Token inválido. Insira um número válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedMovie == "Filme não selecionado" || selectedDirector == "Diretor não selecionado") {
                Toast.makeText(this, "Você precisa votar no Filme e no Diretor.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mandaVotoServer(token, selectedMovie!!, selectedDirector!!)
        }

        findViewById<Button>(R.id.btVoltar5).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }
    }



    private fun mandaVotoServer(token: Int, movieName: String, directorName: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitClient.instance.submitVote(VoteRequest(token, movieName, directorName))
                if (response.isSuccessful) {
                    AlertDialog.Builder(this@TelaConfirmarVoto)
                        .setTitle("Sucesso")
                        .setMessage("Votação realizada com sucesso!")
                        .setPositiveButton("OK") { _, _ ->
                            votesSubmitted = true

                            // Disable further modifications
                            val sharedPreferences = getSharedPreferences("UserVotes", Context.MODE_PRIVATE)
                            sharedPreferences.edit().putBoolean("votesSubmitted", true).apply()

                            // Redirect or close activity
                            val intent = Intent(this@TelaConfirmarVoto, MainActivity::class.java)
                            startActivity(intent)
                        }
                        .show()
                } else {
                    AlertDialog.Builder(this@TelaConfirmarVoto)
                        .setTitle("Erro")
                        .setMessage("Erro ao registrar voto: ${response.errorBody()?.string()}")
                        .setPositiveButton("OK", null)
                        .show()
                }
            } catch (e: Exception) {
                AlertDialog.Builder(this@TelaConfirmarVoto)
                    .setTitle("Erro")
                    .setMessage("Erro: ${e.message}")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}
