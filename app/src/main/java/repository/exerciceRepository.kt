package com.example.fitnessapp.repository

import com.example.fitnessapp.data.exerciceDao
import com.example.fitnessapp.data.exerciceEntity
import kotlinx.coroutines.flow.Flow

class exerciceRepository(private val dao: exerciceDao) {

    val tousLesExercices: Flow<List<exerciceEntity>> = dao.obtenirTousLesExercices()

    suspend fun inserer(exercice: exerciceEntity) {
        dao.insererExercice(exercice)
    }

    suspend fun supprimer(exercice: exerciceEntity) {
        dao.supprimerExercice(exercice)
    }

    suspend fun mettreAJour(exercice: exerciceEntity) {
        dao.modifierExercice(exercice)
    }


}