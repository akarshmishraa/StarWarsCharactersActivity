package com.example.starwarscharactersactivity.domain.repository
import com.example.starwarscharactersactivity.domain.model.Films
import com.example.starwarscharactersactivity.domain.model.Planets
import com.example.starwarscharactersactivity.domain.model.Results
import com.example.starwarscharactersactivity.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCharacters(
        page: Int,
        category: String?,
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Results>>>

    suspend fun getCharactersInfo(
        number: String
    ): Resource<Results>

    suspend fun getCharacterFilms(
        url: String
    ): Resource<Films>

    suspend fun getHairColors(): Resource<List<String>>
    suspend fun getEyeColors(): Resource<List<String>>
    suspend fun sortCharacters(query: String): Resource<List<Results>>
}