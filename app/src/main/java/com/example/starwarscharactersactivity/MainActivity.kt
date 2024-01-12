package com.example.starwarscharactersactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarscharactersactivity.presenter.home.CharactersFragment
import com.example.starwarscharactersactivity.presenter.home.filters.FilterBottomsheet
import com.example.starwarscharactersactivity.presenter.home.sorting.SortingBottomsheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FilterBottomsheet.FilterOptions,
    SortingBottomsheet.SortingOptions {
    val fragment = CharactersFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openCharacterFragment()
    }

    private fun openCharacterFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, fragment)
            .commit()
    }

    override fun filteredOptions(category: String, query: String) {
        fragment.filteredOptions(category, query)
    }

    override fun sortingOptions(query: String) {
        fragment.sortedOptions(query)
    }
}