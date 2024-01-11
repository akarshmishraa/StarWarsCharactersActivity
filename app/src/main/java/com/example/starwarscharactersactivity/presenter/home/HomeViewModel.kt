package com.example.starwarscharactersactivity.presenter.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarscharactersactivity.domain.repository.Repository
import com.example.starwarscharactersactivity.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private var curPage = 0

    var states = MutableLiveData<HomeScreenStates>().apply {
        value = HomeScreenStates()
    }

    private var searchJob: Job? = null

    init {
        getCharacters()
    }

    fun onEvents(events: HomeScreenEvents) {
        when (events) {
            is HomeScreenEvents.isRefreshing -> {
                getCharacters(fetchFromRemote = true)
            }

            is HomeScreenEvents.OnSearchQueryChanged -> {
                states.value = states.value?.copy(searchQuery = events.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCharacters()
                }
            }
        }
    }

    fun getCharacters(
        query: String = states.value?.searchQuery?.lowercase().toString(),
        fetchFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            states.value = states.value?.copy(isLoading = true)
            repository.getCharacters(page = 1 + curPage, query, fetchFromRemote)
                .collect { resultsEntity ->
                    when (resultsEntity) {
                        is Resource.Success -> {
                            resultsEntity.data?.let {
                                states.value = states.value?.copy(results = it)
                            }
                            curPage++
                        }

                        is Resource.Error -> {
                            states.value = states.value?.copy(
                                results = emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            states.value = states.value?.copy(
                                isLoading = resultsEntity.isLoading
                            )
                        }
                    }
                }
        }
    }
}