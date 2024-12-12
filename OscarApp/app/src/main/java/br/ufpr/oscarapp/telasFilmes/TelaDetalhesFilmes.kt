package br.ufpr.oscarapp.telasFilmes

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.oscarapp.R
import com.squareup.picasso.Picasso

class TelaDetalhesFilmes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_detalhes_filmes)

        // Ajusta as margens do sistema (opcional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura o botão de voltar
        findViewById<Button>(R.id.btnVoltarFilme).setOnClickListener { finish() }

        // Obtém os dados enviados pela Intent
        val nomeFilme = intent.getStringExtra("nomeFilme")
        val generoFilme = intent.getStringExtra("generoFilme")
        val fotoFilme = intent.getStringExtra("fotoFilme") // URL da imagem

        // Vincula os elementos de UI
        val filmeNomeTextView = findViewById<TextView>(R.id.NomeFilme2)
        val filmeGeneroTextView = findViewById<TextView>(R.id.generoFilme2)
        val filmeFotoImageView = findViewById<ImageView>(R.id.fotoFilme2)

        // Preenche os dados
        filmeNomeTextView.text = nomeFilme
        filmeGeneroTextView.text = generoFilme

        // Carrega a imagem usando Picasso
        if (!fotoFilme.isNullOrEmpty()) {
            Picasso.get()
                .load(fotoFilme)
                .placeholder(R.drawable.placeholder_image) // Imagem exibida enquanto carrega
                .error(R.drawable.error_image) // Imagem em caso de erro
                .into(filmeFotoImageView)
        } else {
            filmeFotoImageView.setImageResource(R.drawable.placeholder_image) // Imagem padrão
        }

        findViewById<Button>(R.id.btnVotar).setOnClickListener {
            salvaLocalmente(nomeFilme ?: "")
            Toast.makeText(this, "Voto salvo temporáriamente para o filme: $nomeFilme", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    fun salvaLocalmente(movieName: String) {
        val sharedPreferences = getSharedPreferences("UserVotes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("selectedMovie", movieName) // Store the selected movie
        editor.apply()
    }
}

