package com.example.starwarscharactersactivity.presenter.home.filters

import androidx.lifecycle.ViewModel
import com.example.starwarscharactersactivity.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    suspend fun getHairColors() = repository.getHairColors()
    suspend fun getEyeColors() = repository.getEyeColors()
}