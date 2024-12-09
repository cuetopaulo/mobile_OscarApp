package br.ufpr.oscarapp

import android.annotation.SuppressLint
import android.app.VoiceInteractor
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TelaJaVotou : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_ja_votou)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvDiretorVotado = findViewById<TextView>(R.id.tvDiretorVotado)
        val tvFilmeVotado = findViewById<TextView>(R.id.tvFilmeVotado)
        val tvTokenVotado = findViewById<TextView>(R.id.tvTokenUsado)
        val tvVotoDiaHora = findViewById<TextView>(R.id.tvVotoDiaHora)

        /*
        incluir as informações do token, dia, hora, do diretor e filme votado conforme webService
         */

        // Botão para ir para SAIR
        findViewById<Button>(R.id.btSair6).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}