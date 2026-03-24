package com.example.fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercices")

data class exerciceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val muscle: String,
    val series: Int,
    val poids: String,
    val imageRes: Int

)