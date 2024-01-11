package com.example.starwarscharactersactivity.presenter.movieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarscharactersactivity.domain.model.Films
import com.example.starwarscharactersactivity.domain.repository.Repository
import com.example.starwarscharactersactivity.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var characterPosition: Int? = null

    var states = MutableLiveData<DetailScreenStates>().apply {
        value = DetailScreenStates()
    }

    private val filmsList: ArrayList<Films?> = ArrayList()
    fun setDataFromFragment(position: Int) {
        characterPosition = position
        init()
    }

    private fun init() {
        viewModelScope.launch {
            states.value = states.value?.copy(isLoading = true)
            if(characterPosition == null){
                return@launch
            }
            val characterInfo = async { repository.getCharactersInfo(characterPosition.toString()) }

            characterInfo.await().data?.films?.forEach { film ->
                val characterFilm = async { repository.getCharacterFilms(film) }
                when (val films = characterFilm.await()) {
                    is Resource.Success -> {
                        filmsList.add(films.data)
                        films.data.let {
                            states.value = states.value?.copy(
                                films = filmsList,
                                isLoading = false,
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        states.value = states.value?.copy(
                            error = films.message,
                            isLoading = false
                        )
                    }

                    else -> {}
                }

            }
        }
    }
}