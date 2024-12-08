package br.ufpr.oscarapp.telasDiretores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.oscarapp.R
import br.ufpr.oscarapp.TelaBoasVindas

class TelaDiretores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_diretores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão para ir para tela boas vindas
        findViewById<Button>(R.id.btVoltar4).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }
    }
}