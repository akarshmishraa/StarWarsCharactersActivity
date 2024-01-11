package com.example.starwarscharactersactivity.presenter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.ItemGridCharacterBinding

class CharacterAdapter(
    private val homeScreenStates: HomeScreenStates,
    private val apiCallback: ApiCallback,
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val itemList = homeScreenStates.results

    class ViewHolder(itemView: ItemGridCharacterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvCharacterName = itemView.tvCharacterName
        val tvBirthPlace = itemView.tvBirthPlace
        val tvBirthYear = itemView.tvBirthYear
        val tvNumberOfFilms = itemView.tvNumberOfFilms
        val clCharacterView = itemView.clCharacterView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_character, parent, false)
        return ViewHolder(ItemGridCharacterBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCharacterName.text = itemList[position].name
        holder.tvBirthPlace.text = itemList[position].homeworld
        holder.tvBirthYear.text = itemList[position].birth_year
        holder.tvNumberOfFilms.text = itemList[position].films.size.toString()
        if (position >= itemList.size - 1 && !homeScreenStates.endReached && !homeScreenStates.isLoading) {
            apiCallback.paginationRequired()
        }
        holder.clCharacterView.setOnClickListener{
            apiCallback.characterClicked(position+1)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

interface ApiCallback {
    fun paginationRequired()
    fun characterClicked(position: Int)
}