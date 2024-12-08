package br.ufpr.oscarapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TelaConfirmarVoto : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_confirmar_voto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão para SALVAR e CONFIRMAR VOTO
        findViewById<Button>(R.id.btSalvarVotoFinalizar).setOnClickListener {
            val filmeEscolhido = findViewById<TextView>(R.id.tvFilmeEscolhido).text.toString()
            val diretorEscolhido = findViewById<TextView>(R.id.tvDiretorEscolhido).text.toString()
            val tokenString = findViewById<TextView>(R.id.tvTokenInserir).text.toString()

            try {
                val token = tokenString.toInt()

                //inserir lógica da votação e confirmação do token
                if (filmeEscolhido != "Filme não selecionado" || diretorEscolhido != "Diretor não selecionado") {
                    if (token == 123456) {  // logica do token do BD
                        AlertDialog.Builder(this)
                            .setTitle("Sucesso")
                            .setMessage("Votação realizada com sucesso!")
                            .setPositiveButton("OK") { dialog, which ->
                                val intent = Intent(this, TelaBoasVindas::class.java)
                                startActivity(intent)
                            }
                            .show()
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Erro")
                            .setMessage("Token inválido")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Erro")
                        .setMessage("Você precisa votar no Filme e no Diretor")
                        .setPositiveButton("OK", null)
                        .show()
                }
            } catch (e: NumberFormatException) {
                // Trate a exceção, por exemplo, exibindo uma mensagem de erro
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Token vazio. Digite o token!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Botão para ir para tela boas vindas
        findViewById<Button>(R.id.btVoltar5).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }
    }
}