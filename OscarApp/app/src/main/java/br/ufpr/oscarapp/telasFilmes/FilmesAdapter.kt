package br.ufpr.oscarapp.telasFilmes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.content.Context
import android.content.Intent
import br.ufpr.oscarapp.R

class FilmesAdapter(
    private var filmes: List<Filme>,
    private val context: Context
) : RecyclerView.Adapter<FilmesAdapter.FilmeViewHolder>() {
    inner class FilmeViewHolder(
        private val itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val fotoFilme: ImageView = itemView.findViewById<ImageView>(R.id.fotoFilme)
        val nomeFilme: TextView = itemView.findViewById<TextView>(R.id.nomeFilme)
        val generoFilme: TextView = itemView.findViewById<TextView>(R.id.generoFilme)
    }

    // Atualiza os filmes no adapter
    fun updateFilmes(filmes: List<Filme>) {
        this.filmes = filmes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FilmeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = filmes[position]
        holder.nomeFilme.text = filme.nome
        holder.generoFilme.text = filme.genero

        // Carregar a imagem usando Picasso
        Picasso.get()
            .load(filme.foto)
            .into(holder.fotoFilme)

        holder.nomeFilme.setOnClickListener {
            val intent = Intent(context, TelaDetalhesFilmes::class.java).apply {
                putExtra("nomeFilme", filme.nome)
                putExtra("generoFilme", filme.genero)
                putExtra("fotoFilme", filme.foto)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filmes.size
    }
}
