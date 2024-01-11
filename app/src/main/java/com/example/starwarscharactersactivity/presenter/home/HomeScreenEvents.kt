package com.example.starwarscharactersactivity.presenter.home

sealed class HomeScreenEvents {

    object isRefreshing : HomeScreenEvents()

    data class OnSearchQueryChanged(val query: String) : HomeScreenEvents()
    data class OnFilterApplied(val category: String, val query: String) : HomeScreenEvents()
}