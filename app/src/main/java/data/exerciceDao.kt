package com.example.fitnessapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface exerciceDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererExercice(exercice: exerciceEntity)


    @Delete
    suspend fun supprimerExercice(exercice: exerciceEntity)


    @Query("SELECT * FROM exercices")
    fun obtenirTousLesExercices(): Flow<List<exerciceEntity>>


    @Query("SELECT * FROM exercices WHERE nom LIKE :nomRecherche")
    fun rechercherParNom(nomRecherche: String): Flow<List<exerciceEntity>>


    @Update
    suspend fun modifierExercice(exercice: exerciceEntity)

}