package br.ufpr.oscarapp.telasFilmes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.oscarapp.R
import br.ufpr.oscarapp.TelaBoasVindas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay

class TelaFilmes : AppCompatActivity() {

    private lateinit var recyclerViewFilmes: RecyclerView
    private lateinit var filmesAdapter: FilmesAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_filmes)

        // Botão Voltar
        findViewById<Button>(R.id.btVoltar3).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }

        // Configuração do RecyclerView
        recyclerViewFilmes = findViewById(R.id.filmesRV)
        progressBar = findViewById(R.id.progressBar)
        filmesAdapter = FilmesAdapter(emptyList(), this) // Inicializa com uma lista vazia
        recyclerViewFilmes.layoutManager = LinearLayoutManager(this)
        recyclerViewFilmes.adapter = filmesAdapter
        recyclerViewFilmes.setHasFixedSize(true)
        recyclerViewFilmes.addItemDecoration(
            DividerItemDecoration(this.applicationContext, DividerItemDecoration.VERTICAL)
        )

        // Carregar dados da API
        fetchFilmes()
    }

    private fun fetchFilmes() {
        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE
                recyclerViewFilmes.visibility = View.GONE
                delay(2000)
                val filmes = withContext(Dispatchers.IO) {
                    ApiFilmesClient.api.getFilmes() // Chamada à API
                }
                Log.d("TelaFilmes", "Filmes recebidos: $filmes")
                filmesAdapter.updateFilmes(filmes) // Atualiza o Adapter com os filmes
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TelaFilmes", "Erro ao carregar filmes", e)
                // Aqui você pode exibir uma mensagem de erro ao usuário
            } finally {
                progressBar.visibility = View.GONE
                recyclerViewFilmes.visibility = View.VISIBLE
            }
        }
    }

}
