package com.example.starwarscharactersactivity.domain.repository
import com.example.starwarscharactersactivity.domain.model.Films
import com.example.starwarscharactersactivity.domain.model.Planets
import com.example.starwarscharactersactivity.domain.model.Results
import com.example.starwarscharactersactivity.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCharacters(
        page: Int,
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Results>>>

    suspend fun getCharactersInfo(
        number: String
    ): Resource<Results>

    suspend fun getCharacterPlanets(
        url: String
    ): Resource<Planets>

    suspend fun getCharacterFilms(
        url: String
    ): Resource<Films>
}