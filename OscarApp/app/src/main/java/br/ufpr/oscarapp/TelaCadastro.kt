package br.ufpr.oscarapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.semantics.text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import androidx.glance.visibility

class TelaCadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botão para Salvar novo usuario
        findViewById<Button>(R.id.btCadastrarSalvarUsu).setOnClickListener {
            // fazer a lógica de cadastrar o usuario
            val confereSenha=findViewById<TextView>(R.id.tvSenha3).text.toString()
            val senha=findViewById<TextView>(R.id.tvSenha2).text.toString()
            val login=findViewById<TextView>(R.id.tvLogin2).text.toString()
            // verificadr no BD se o login já existe
            if(login=="BuscarNoBD"){    //trocar a lógica para buscar no BD
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Usuário já possui conta Cadastrada")
                    .setPositiveButton("OK", null) // Não precisa de listener para o botão "OK" neste caso
                    .show()
            }
            else {
                if (login.isNotEmpty() && senha == confereSenha && senha.isNotEmpty() && confereSenha.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setTitle("Sucesso")
                        .setMessage("Usuário cadastrado com sucesso")
                        .setPositiveButton("OK") { dialog, which ->
                            // Código a ser executado após o clique em "OK"
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        .show()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Erro")
                        .setMessage("Usuário não pode ser vazio e as senhas devem ser iguais")
                        .setPositiveButton(
                            "OK",
                            null
                        ) // Não precisa de listener para o botão "OK" neste caso
                        .show()
                    return@setOnClickListener
                }
            }

        }

        // Botão para ir para tela Inicial
        findViewById<Button>(R.id.btVoltar).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}