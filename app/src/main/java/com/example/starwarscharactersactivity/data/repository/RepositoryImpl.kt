package com.example.starwarscharactersactivity.data.repository

import com.example.starwarscharactersactivity.data.local.StarDatabase
import com.example.starwarscharactersactivity.data.remote.Api
import com.example.starwarscharactersactivity.domain.model.Films
import com.example.starwarscharactersactivity.domain.model.Planets
import com.example.starwarscharactersactivity.domain.model.Results
import com.example.starwarscharactersactivity.domain.repository.Repository
import com.example.starwarscharactersactivity.domain.utils.Resource
import com.richmondprojects.myapplication.data.mappers.toFilmsDomain
import com.richmondprojects.myapplication.data.mappers.toPlanetsDomain
import com.richmondprojects.myapplication.data.mappers.toResults
import com.richmondprojects.myapplication.data.mappers.toResultsDomain
import com.richmondprojects.myapplication.data.mappers.toResultsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: Api,
    db: StarDatabase,
) : Repository {

    private val dao = db.dao

    override suspend fun getCharacters(
        page: Int,
        query: String,
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<Results>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCache = dao.searchCharacters(query)
            emit(Resource.Success(localCache.map { it.toResults() }))

            val isDBEmpty = localCache.isEmpty() && query.isBlank()

            val shouldJustLoadFromCache = !isDBEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val response = try {
                val result = api.getCharacters(page)
                result.results.map { it.toResultsDomain() }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message()))
                null
            }

            response?.let { result ->
                dao.insertCharacters(result.map { it.toResultsEntity() })
                emit(Resource.Success(data = dao.searchCharacters("").map { it.toResults() }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCharactersInfo(number: String): Resource<Results> {
        return try {
            val results = api.getCharacterInfo(number)
            Resource.Success(results.toResultsDomain())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage)
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message())
        }
    }

    override suspend fun getCharacterPlanets(url: String): Resource<Planets> {
        return try {
            val response = api.getCharacterPlanet(url)
            Resource.Success(response.toPlanetsDomain())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage)
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message())
        }
    }

    override suspend fun getCharacterFilms(url: String): Resource<Films> {
        return try {
            val films = api.getCharacterFilms(url)
            Resource.Success(films.toFilmsDomain())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage)
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message())
        }
    }
}