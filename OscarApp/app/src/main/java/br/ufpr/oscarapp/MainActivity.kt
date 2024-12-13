package br.ufpr.oscarapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        findViewById<Button>(R.id.btIP).setOnClickListener {
            val intent = Intent(this, TelaIP::class.java)
            startActivity(intent)
        }



        // Botão para login
        findViewById<Button>(R.id.btEntrar).setOnClickListener {
            val login = findViewById<TextView>(R.id.tvLogin).text.toString()
            val senha = findViewById<TextView>(R.id.tvSenha).text.toString()

            if (login.isNotEmpty() && senha.isNotEmpty()) {
                autentica(login, senha)
                val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
                sharedPreferences.edit().putString("login", login).apply()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Usuário ou senha não podem estar vazios")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    private fun autentica(login: String, senha: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitClient.instance.login(LoginRequest(login, senha))
                if (response.isSuccessful) {
                    val token = response.body()?.token

                    val jaVotou = checaSeVotou(login)

                    if (jaVotou) {
                        val intent = Intent(this@MainActivity, TelaJaVotou::class.java)
                        startActivity(intent)
                    } else {
                        salvaToken(token)
                        val intent = Intent(this@MainActivity, TelaBoasVindas::class.java)
                        startActivity(intent)
                    }
                } else {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Erro")
                        .setMessage("Login ou senha incorretos")
                        .setPositiveButton("OK", null)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erro: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun salvaToken(token: Int?) {
        val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        sharedPreferences.edit().putInt("token", token ?: -1).apply()
    }

    private suspend fun checaSeVotou(login: String): Boolean {
        if (login.isEmpty()) {
            Toast.makeText(this, "Login inválido. Faça login novamente.", Toast.LENGTH_SHORT).show()
            return false
        }

        return try {
            val response = RetrofitClient.instance.checkVote(CheckVoteRequest(login))
            if (response.isSuccessful) {
                response.body()?.voted ?: false
            } else {
                Toast.makeText(this, "Failed to check voting status: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                false
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            false
        }
    }

}
