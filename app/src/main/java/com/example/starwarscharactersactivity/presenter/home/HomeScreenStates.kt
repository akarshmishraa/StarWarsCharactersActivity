package com.example.starwarscharactersactivity.presenter.home

import com.example.starwarscharactersactivity.domain.model.Results

data class HomeScreenStates(
    val results: List<Results> = emptyList(),
    val isLoading: Boolean = false,
    val refresh: Boolean = false,
    val searchQuery: String = "",
    val endReached: Boolean = false,
    val category : String? = null
)