package br.ufpr.oscarapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TelaJaVotou : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_ja_votou)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val login = sharedPreferences.getString("login", "") ?: ""


        val tvDiretorVotado = findViewById<TextView>(R.id.tvDiretorVotado)
        val tvFilmeVotado = findViewById<TextView>(R.id.tvFilmeVotado)
        val tvVotoDiaHora = findViewById<TextView>(R.id.tvVotoDiaHora)


        if (login.isNotEmpty()) {
            fetchVoteDetails(login, tvFilmeVotado, tvDiretorVotado, tvVotoDiaHora)
        } else {
            Toast.makeText(
                this,
                "Login inválido. Por favor, faça login novamente.",
                Toast.LENGTH_SHORT
            ).show()
        }


        findViewById<Button>(R.id.btSair6).setOnClickListener {
            android.app.AlertDialog.Builder(this) // Use android.app.AlertDialog.Builder
                .setTitle("Confirmação")
                .setMessage("Tem certeza que deseja sair?")
                .setPositiveButton("OK") { dialog, which ->
                    finishAffinity()
                }
                .setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }

        private fun fetchVoteDetails(
            login: String,
            tvFilmeVotado: TextView,
            tvDiretorVotado: TextView,
            tvVotoDiaHora: TextView
        ) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response =
                        RetrofitClient.instance.getVoteDetails(GetVoteDetailsRequest(login))
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val voteDetails = response.body()
                            voteDetails?.let {
                                tvFilmeVotado.text = "Filme: ${it.filme}"
                                tvDiretorVotado.text = "Diretor: ${it.diretor}"
                                tvVotoDiaHora.text = "Voto registrado em: ${it.timestamp}"
                            }
                        } else {
                            Toast.makeText(
                                this@TelaJaVotou,
                                "Erro ao carregar os detalhes do voto",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@TelaJaVotou, "Erro: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }





