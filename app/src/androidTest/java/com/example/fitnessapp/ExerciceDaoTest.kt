package com.example.fitnessapp


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitnessapp.data.appDatabase
import com.example.fitnessapp.data.exerciceDao
import com.example.fitnessapp.data.exerciceEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExerciceDaoTest {

    private lateinit var db: appDatabase
    private lateinit var dao: exerciceDao


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, appDatabase::class.java).build()
        dao = db.exerciceDao()
    }


    @After
    fun tearDown() {
        db.close()
    }


    @Test
    fun testAjouterExercice() = runBlocking {

        val exercice = exerciceEntity(0, "Développé Couché", "Pectoraux", 4, "80", 0)
        dao.insererExercice(exercice)

        val liste = dao.obtenirTousLesExercices().first()
        assertEquals("Développé Couché", liste[0].nom)
    }


    @Test
    fun testSupprimerExercice() = runBlocking {
        val exercice = exerciceEntity(1, "Squat", "Jambes", 3, "100", 0)
        dao.insererExercice(exercice)
        dao.supprimerExercice(exercice)

        val liste = dao.obtenirTousLesExercices().first()
        assertTrue(liste.isEmpty())
    }


    @Test
    fun testModifierExercice() = runBlocking {
        val exercice = exerciceEntity(2, "Curl", "Biceps", 3, "10", 0)
        dao.insererExercice(exercice)


        val exerciceModifie = exercice.copy(poids = "15")
        dao.modifierExercice(exerciceModifie)

        val liste = dao.obtenirTousLesExercices().first()
        assertEquals("15", liste[0].poids)
    }
}