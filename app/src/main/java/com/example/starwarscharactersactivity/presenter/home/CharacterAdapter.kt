package com.example.starwarscharactersactivity.presenter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharactersactivity.R
import com.example.starwarscharactersactivity.databinding.ItemGridCharacterBinding

class CharacterAdapter(
    private val homeScreenStates: HomeScreenStates,
    private val isFilterApplied: Boolean,
    private val apiCallback: ApiCallback,
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val itemList = homeScreenStates.results

    class ViewHolder(itemView: ItemGridCharacterBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvCharacterName = itemView.tvCharacterName
        val tvHeight = itemView.tvHeight
        val tvBirthYear = itemView.tvBirthYear
        val tvMass = itemView.tvMass
        val clCharacterView = itemView.clCharacterView
        val tvEyeColor = itemView.tvEyeColor
        val tvHairColor = itemView.tvHairColor
        val tvGender = itemView.tvGender
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid_character, parent, false)
        return ViewHolder(ItemGridCharacterBinding.bind(view))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCharacterName.text = "Name : ${ itemList[position].name }"
        holder.tvHeight.text = "Height : ${ itemList[position].height }"
        holder.tvBirthYear.text = "Birth Year : ${ itemList[position].birth_year }"
        holder.tvMass.text = "Mass: ${itemList[position].mass}"
        holder.tvEyeColor.text = "Eye Color: ${itemList[position].eye_color}"
        holder.tvHairColor.text = "Hair Color: ${itemList[position].hair_color}"
        holder.tvGender.text = "Gender: ${itemList[position].gender}"
        if (position >= itemList.size - 1 && !homeScreenStates.endReached && !homeScreenStates.isLoading && !isFilterApplied) {
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