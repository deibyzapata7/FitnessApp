package com.example.fitnessapp


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "parametres")

class SettingsManager(context: Context) {
    private val dataStore = context.dataStore
    private val MODE_SOMBRE_KEY = booleanPreferencesKey("mode_sombre")


    val modeSombre: Flow<Boolean> = dataStore.data.map { pref ->
        pref[MODE_SOMBRE_KEY] ?: false
    }


    suspend fun sauvegarderModeSombre(estSombre: Boolean) {
        dataStore.edit { pref ->
            pref[MODE_SOMBRE_KEY] = estSombre
        }
    }
}