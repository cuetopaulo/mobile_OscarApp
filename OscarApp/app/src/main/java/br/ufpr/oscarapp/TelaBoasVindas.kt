package br.ufpr.oscarapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.oscarapp.telasDiretores.TelaDiretores
import br.ufpr.oscarapp.telasFilmes.TelaFilmes

class TelaBoasVindas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_boas_vindas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão para ir para tela VOTAR FILME
        findViewById<Button>(R.id.btVotarFilme).setOnClickListener {
            val intent = Intent(this, TelaFilmes::class.java)
            startActivity(intent)
        }

        // Botão para ir para tela VOTAR DIRETOR
        findViewById<Button>(R.id.btVotarDiretor).setOnClickListener {
            val intent = Intent(this, TelaDiretores::class.java)
            startActivity(intent)
        }

        // Botão para ir para tela CONFIRMAR VOTO
        findViewById<Button>(R.id.btConfirmarVoto).setOnClickListener {
            val intent = Intent(this, TelaConfirmarVoto::class.java)
            startActivity(intent)
        }

        // Botão para SAIR
        findViewById<Button>(R.id.btSair).setOnClickListener {
            android.app.AlertDialog.Builder(this) // Use android.app.AlertDialog.Builder
                .setTitle("Confirmação")
                .setMessage("Tem certeza que deseja sair?\nSeus votos não serão salvos nem registrados!")
                .setPositiveButton("OK") { dialog, which ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}