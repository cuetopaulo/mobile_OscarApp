package br.ufpr.oscarapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
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
            //inserir lógica da votação e confirmação do token
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }

        // Botão para ir para tela boas vindas
        findViewById<Button>(R.id.btVoltar5).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }
    }
}