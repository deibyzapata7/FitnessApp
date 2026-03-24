package com.example.fitnessapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fitnessapp.data.exerciceEntity
import com.example.fitnessapp.repository.exerciceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class exerciceViewModel(private val repository: exerciceRepository) : ViewModel() {


    val tousLesExercices: Flow<List<exerciceEntity>> = repository.tousLesExercices


    fun ajouterExercice(
        nom: String,
        muscle: String,
        series: Int,
        poids: String,
        imageRes: Int
    ) {
        viewModelScope.launch {

            val nouvelExercice = exerciceEntity(
                nom = nom,
                muscle = muscle,
                series = series,
                poids = poids,
                imageRes = imageRes

            )
            repository.inserer(nouvelExercice)
        }
    }


    fun supprimerExercice(exercice: exerciceEntity) {
        viewModelScope.launch {
            repository.supprimer(exercice)
        }
    }

    fun mettreAJourExercice(exercice: exerciceEntity) {
        viewModelScope.launch {

            repository.mettreAJour(exercice)
        }
    }


}

class exerciceViewModelFactory(private val repository: exerciceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(exerciceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return exerciceViewModel(repository) as T
        }

        throw IllegalArgumentException("Classe ViewModel inconnue")
    }
}