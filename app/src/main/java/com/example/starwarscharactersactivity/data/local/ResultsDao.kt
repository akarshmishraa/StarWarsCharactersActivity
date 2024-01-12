package com.example.starwarscharactersactivity.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(resultsEntity: List<ResultsEntity>)

    @Query("DELETE FROM resultsentity")
    suspend fun clearCharacters()

    @Query(
        """
        SELECT * FROM resultsentity
        WHERE LOWER(name) LIKE '%'|| LOWER(:query) || '%'
        OR LOWER(:query) == LOWER(eye_color)
    """
    )
    suspend fun searchCharacters(query: String): List<ResultsEntity>

    @Query(
        """
        SELECT * FROM resultsentity
        WHERE LOWER(gender) LIKE '%'|| LOWER(:query) || '%'
    """
    )
    suspend fun searchGender(query: String): List<ResultsEntity>
    @Query(
        """
        SELECT * FROM resultsentity
        WHERE LOWER(hair_color) LIKE '%'|| LOWER(:query) || '%'
    """
    )
    suspend fun searchHairColor(query: String): List<ResultsEntity>
    @Query(
        """
        SELECT * FROM resultsentity
        WHERE LOWER(eye_color) LIKE '%'|| LOWER(:query) || '%'
    """
    )
    suspend fun searchEyeColor(query: String): List<ResultsEntity>
    @Query("SELECT DISTINCT hair_color FROM resultsentity")
    suspend fun getHairColors(): List<String>
    @Query("SELECT DISTINCT eye_color FROM resultsentity")
    suspend fun getEyeColors(): List<String>

    @Query("SELECT * FROM resultsentity\n" +
            "ORDER BY mass ASC;\n")
    suspend fun sortMassL2H(): List<ResultsEntity>
    @Query("SELECT * FROM resultsentity\n" +
            "ORDER BY mass DESC;\n")
    suspend fun sortMassH2L(): List<ResultsEntity>

    @Query("SELECT * FROM resultsentity\n" +
            "ORDER BY height ASC;\n")
    suspend fun sortHeightL2H(): List<ResultsEntity>
    @Query("SELECT * FROM resultsentity\n" +
            "ORDER BY height DESC;\n")
    suspend fun sortHeightH2L(): List<ResultsEntity>

}