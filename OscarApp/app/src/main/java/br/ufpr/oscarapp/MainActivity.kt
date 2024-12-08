package br.ufpr.oscarapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão para ir para tela de cadastro
        findViewById<Button>(R.id.btCadastrar).setOnClickListener {
            val intent = Intent(this, TelaCadastro::class.java)
            startActivity(intent)
        }
        // Botão para ir para tela de BOAS VINDAS
        findViewById<Button>(R.id.btEntrar).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }


    }




}