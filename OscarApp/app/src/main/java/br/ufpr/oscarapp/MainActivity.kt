package br.ufpr.oscarapp

import AuthRequest
import AuthService
import TokenResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.ufpr.oscarapp.oscarApi.InfoGeralApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //private lateinit var aaa: TextView
    private lateinit var infoGeralApi: InfoGeralApi




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViewById<Button>(R.id.btEntrar).setOnClickListener {
            val login = findViewById<TextView>(R.id.tvLogin).text.toString()
            val senha = findViewById<TextView>(R.id.tvSenha).text.toString()

            lifecycleScope.launch {
                try {
                    authenticateUser(login, senha)




                } catch (e: Exception) {
                    Log.e("MainActivity", "Erro ao acessar o servidor", e)
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Erro")
                        .setMessage("Erro ao acessar o servidor")
                        .setPositiveButton("OK", null)
                        .show()
                }


            }



        }








        /*
        //Criando retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8000/api/")   // VER ISSO AQUI
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        infoGeralApi = retrofit.create(InfoGeralApi::class.java)


        // Botão para ir para tela de cadastro
        findViewById<Button>(R.id.btCadastrar).setOnClickListener {
            val intent = Intent(this, TelaCadastro::class.java)
            startActivity(intent)
        }
        // Botão para ir para tela de BOAS VINDAS
        findViewById<Button>(R.id.btEntrar).setOnClickListener {
            val login = findViewById<TextView>(R.id.tvLogin).text.toString()
            val senha = findViewById<TextView>(R.id.tvSenha).text.toString()

            if (login.isNotEmpty() && senha.isNotEmpty()) {

                lifecycleScope.launch {
                    try {
                        val webLogin = withContext(Dispatchers.IO) {
                            infoGeralApi.getLogin(login)
                        }
                        val webSenha = withContext(Dispatchers.IO) {
                            infoGeralApi.getSenha(senha)
                        }
                        if (webLogin.login == login && webSenha.senha == senha) {
                            vaiTelaBoasVindas()
                        }
                        else {
                            AlertDialog.Builder(this@MainActivity)
                                .setTitle("Erro")
                                .setMessage("Usuário não cadastrado ou senha incorreta")
                                .setPositiveButton("OK", null) //
                        }
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Erro ao acessar o servidor", e)
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Erro")
                            .setMessage("Erro ao acessar o servidor")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }

                /*
                if ( login=="123" && senha=="123") {    //alterar para login e senha do usuário
                    // verificar no webService se o usuário já votou
                    val intent = Intent(this, TelaJaVotou::class.java)
                    startActivity(intent)

                } else {

                //val intent = Intent(this, TelaBoasVindas::class.java)
                //startActivity(intent)

                }
                */
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("Erro")
                    .setMessage("Os campos de login e senha não podem estar vazios")
                    .setPositiveButton("OK", null) // Não precisa de listener para o botão "OK" neste caso
                    .show()
            }
        }
        */

    }

    //mudar de telas
    private fun vaiTelaBoasVindas() {
        val intent = Intent(this, TelaBoasVindas::class.java)
        startActivity(intent)
    }
    private fun vaiTelaCadastro() {
        val intent = Intent(this, TelaCadastro::class.java)
        startActivity(intent)
    }
    private fun vaiTelaJaVotou() {
        val intent = Intent(this, TelaJaVotou::class.java)
        startActivity(intent)
    }


    object RetrofitClient {
        private const val BASE_URL = "http://10.0.2.2:8000/api/"

        val instance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }


    fun authenticateUser(username: String, password: String) {
        val authService = RetrofitClient.instance.create(AuthService::class.java)
        val request = AuthRequest(username, password)

        authService.authenticate(request).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {

                    //apagar essa parte
                    vaiTelaBoasVindas()
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("BOAAAAA")
                        .setMessage("ENTROU")
                        .setPositiveButton("OK", null)
                        .show()


                    /////

                    val tokenResponse = response.body()
                    tokenResponse?.let {
                        println("Access Token: ${it.access}")
                        println("Refresh Token: ${it.refresh}")
                    }
                } else {
                    println("Authentication failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })
    }



}