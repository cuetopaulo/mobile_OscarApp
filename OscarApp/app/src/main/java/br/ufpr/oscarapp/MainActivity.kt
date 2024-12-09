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
import br.ufpr.oscarapp.API.LoginRequest
import br.ufpr.oscarapp.API.LoginResponse
import br.ufpr.oscarapp.API.RetrofitUnico
import br.ufpr.oscarapp.telasFilmes.TelaFilmes

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            loginWebService(login, senha)






            /*
            if (login.isNotEmpty() && senha.isNotEmpty()) {

                if ( login=="123" && senha=="123") {    //alterar para login e senha do usuário
                    // verificar no webService se o usuário já votou
                    val intent = Intent(this, TelaJaVotou::class.java)
                    startActivity(intent)

                } else {

                val intent = Intent(this, TelaBoasVindas::class.java)
                startActivity(intent)

                }

            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Usuário não cadastrado ou senha incorreta")
                    .setPositiveButton("OK", null) // Não precisa de listener para o botão "OK" neste caso
                    .show()
            }

             */
        }




    }

    fun loginWebService(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        RetrofitUnico.api.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {


                    vaiTela1()


                    // Sucesso: armazene o token e o token de votação
                    //val token = response.body()?.token
                    //val tokenVotacao = response.body()?.token_votacao
                    // Salve o token em SharedPreferences ou em um local seguro
                } else {
                    // Trate erros de login aqui

                    vaiTela2()

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Trate falhas de rede ou outros problemas

                vaiTela3()

            }
        })
    }

    fun vaiTela1(){
        val intent = Intent(this, TelaBoasVindas::class.java)
        startActivity(intent)
    }
    fun vaiTela2(){
        val intent = Intent(this, TelaCadastro::class.java)
        startActivity(intent)
    }
    fun vaiTela3(){
        val intent = Intent(this, TelaFilmes::class.java)
        startActivity(intent)
    }


}