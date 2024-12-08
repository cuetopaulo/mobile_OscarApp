package br.ufpr.oscarapp.telasFilmes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import br.ufpr.oscarapp.R

class FilmesAdapter(private var filmes: List<Filme>) : RecyclerView.Adapter<FilmesAdapter.FilmeViewHolder>() {

    // ViewHolder
    class FilmeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fotoFilme: ImageView = itemView.findViewById(R.id.fotoFilme)
        val nomeFilme: TextView = itemView.findViewById(R.id.nomeFilme)
        val generoFilme: TextView = itemView.findViewById(R.id.generoFilme)
    }

    // Atualiza os filmes no adapter
    fun updateFilmes(filmes: List<Filme>) {
        this.filmes = filmes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FilmeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = filmes[position]
        holder.nomeFilme.text = filme.nome
        holder.generoFilme.text = filme.genero

        // Carregar a imagem usando Picasso
        Picasso.get()
            .load(filme.foto)
            .into(holder.fotoFilme)
    }

    override fun getItemCount(): Int {
        return filmes.size
    }
}
