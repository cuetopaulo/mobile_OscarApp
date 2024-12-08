package br.ufpr.oscarapp.telasFilmes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.oscarapp.R
import br.ufpr.oscarapp.TelaBoasVindas

class TelaFilmes : AppCompatActivity() {

    private lateinit var recyclerViewFilmes: RecyclerView
    private lateinit var filmesAdapter: FilmesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_filmes)

        // Configurar botão "Voltar"
        findViewById<Button>(R.id.btVoltar3).setOnClickListener {
            val intent = Intent(this, TelaBoasVindas::class.java)
            startActivity(intent)
        }

        // Configurar RecyclerView
        recyclerViewFilmes = findViewById(R.id.filmesRV)
        filmesAdapter = FilmesAdapter(getStaticFilmes())
        recyclerViewFilmes.layoutManager = LinearLayoutManager(this)
        recyclerViewFilmes.adapter = filmesAdapter
        recyclerViewFilmes.setHasFixedSize(true)
        recyclerViewFilmes.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    // Dados estáticos de filmes
    private fun getStaticFilmes(): List<Filmes> {
        return listOf(
            Filmes("1", "O Poderoso Chefão", "Drama", R.drawable.poderoso_chefao),
            Filmes("2", "Titanic", "Romance", R.drawable.titanic),
            Filmes("3", "Inception", "Ficção Científica", R.drawable.inception),
            Filmes("4", "Matrix", "Ação", R.drawable.matrix),
            Filmes("5", "Avatar", "Ficção Científica", R.drawable.avatar)
        )
    }
}
