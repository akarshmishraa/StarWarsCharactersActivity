package com.example.starwarscharactersactivity.presenter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.ItemGridCharacterBinding
import com.example.starwarscharactersactivity.domain.model.Results

class CharacterAdapter(private val itemList: List<Results>) :
    RecyclerView.Adapter<CharacterAdapter.YourViewHolder>() {

    class YourViewHolder(itemView: ItemGridCharacterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvCharacterName = itemView.tvCharacterName
        val tvBirthPlace = itemView.tvBirthPlace
        val tvBirthYear = itemView.tvBirthYear
        val tvNumberOfFilms = itemView.tvNumberOfFilms
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_character, parent, false)
        return YourViewHolder(ItemGridCharacterBinding.bind(view))
    }

    override fun onBindViewHolder(holder: YourViewHolder, position: Int) {
        holder.tvCharacterName.text = itemList[position].name
        holder.tvBirthPlace.text = itemList[position].homeworld
        holder.tvBirthYear.text = itemList[position].birth_year
        holder.tvNumberOfFilms.text = itemList[position].films.size.toString()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}