package com.example.starwarscharactersactivity.presenter.movieDetails

import com.example.starwarscharactersactivity.domain.model.Films
import com.example.starwarscharactersactivity.domain.model.Results

data class DetailScreenStates(
    val results: Results? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val films: ArrayList<Films?> = ArrayList(),
)
