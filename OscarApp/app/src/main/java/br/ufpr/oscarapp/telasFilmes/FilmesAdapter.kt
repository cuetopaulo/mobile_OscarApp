package br.ufpr.oscarapp.telasFilmes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.oscarapp.R
import com.squareup.picasso.Picasso

class FilmesAdapter(private val filmes: List<Filmes>) :
    RecyclerView.Adapter<FilmesAdapter.FilmesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return FilmesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmesViewHolder, position: Int) {
        val filme = filmes[position]
        holder.nomeFilme.text = filme.nome
        holder.generoFilme.text = filme.genero

        // Carregar imagem do drawable
        Picasso.get()
            .load(filme.foto)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.fotoFilme)
    }

    override fun getItemCount(): Int = filmes.size

    class FilmesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeFilme: TextView = view.findViewById(R.id.nomeFilme)
        val generoFilme: TextView = view.findViewById(R.id.generoFilme)
        val fotoFilme: ImageView = view.findViewById(R.id.fotoFilme)
    }
}
