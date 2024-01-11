package com.example.starwarscharactersactivity.presenter.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharactersactivity.databinding.ItemGridMoviesBinding

class MoviesAdapter(private val detailScreenStates: DetailScreenStates) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    class ViewHolder(itemView: ItemGridMoviesBinding) : RecyclerView.ViewHolder(itemView.root) {
        val tvMovieName = itemView.tvMovieName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGridMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMovieName.text = detailScreenStates.films[position]?.title ?: "N/A"
    }

    override fun getItemCount(): Int {
        return detailScreenStates.films.size
    }

}