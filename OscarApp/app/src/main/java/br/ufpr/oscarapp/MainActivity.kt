package br.ufpr.oscarapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
            val login = findViewById<TextView>(R.id.tvLogin).text.toString()
            val senha = findViewById<TextView>(R.id.tvSenha).text.toString()

            if (login.isNotEmpty() && senha.isNotEmpty() /*&& logica de buscar no BD*/) {
                val intent = Intent(this, TelaBoasVindas::class.java)
                startActivity(intent)
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Usuário não cadastrado ou senha incorreta")
                    .setPositiveButton("OK", null) // Não precisa de listener para o botão "OK" neste caso
                    .show()
            }
        }


    }




}