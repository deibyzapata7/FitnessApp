package com.example.fitnessapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessapp.data.appDatabase
import com.example.fitnessapp.data.exerciceEntity
import com.example.fitnessapp.repository.exerciceRepository
import com.example.fitnessapp.viewmodel.exerciceViewModel
import com.example.fitnessapp.viewmodel.exerciceViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val baseDeDonnees = appDatabase.obtenirBaseDeDonnees(this)
        val depot = exerciceRepository(baseDeDonnees.exerciceDao())
        val fabrique = exerciceViewModelFactory(depot)

        val gestionnaireParametres = SettingsManager(this)

        setContent {

            val estModeSombre by gestionnaireParametres.modeSombre.collectAsState(initial = false)
            val porteeCoroutine = rememberCoroutineScope()

            MaterialTheme(
                colorScheme = if (estModeSombre) darkColorScheme() else lightColorScheme()
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {

                    Box(modifier = Modifier.fillMaxSize()) {


                        Image(
                            painter = painterResource(id = R.drawable.gym),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alpha = if (estModeSombre) 0.25f else 0.55f
                        )


                        EcranFitness(
                            fabrique = fabrique,
                            estModeSombre = estModeSombre,
                            surChangementTheme = { nuevoValor ->
                                porteeCoroutine.launch {
                                    gestionnaireParametres.sauvegarderModeSombre(nuevoValor)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun EcranFitness(
    fabrique: exerciceViewModelFactory,
    estModeSombre: Boolean,
    surChangementTheme: (Boolean) -> Unit
) {
    val monViewModel: exerciceViewModel = viewModel(factory = fabrique)
    val listeExercices by monViewModel.tousLesExercices.collectAsState(initial = emptyList())

    var exerciceAEditar by remember { mutableStateOf<exerciceEntity?>(null) }
    var texteRecherche by remember { mutableStateOf("") }
    var nomSaisi by remember { mutableStateOf("") }
    var poidsSaisi by remember { mutableStateOf("") }
    var seriesSaisies by remember { mutableStateOf("") }

    val listaFiltrada by remember(texteRecherche, listeExercices) {
        derivedStateOf {
            if (texteRecherche.isEmpty()) listeExercices
            else listeExercices.filter { it.nom.contains(texteRecherche, ignoreCase = true) }
        }
    }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Fitness App",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.weight(1f)
            )
            Switch(checked = estModeSombre, onCheckedChange = surChangementTheme)
        }


        OutlinedTextField(
            value = texteRecherche,
            onValueChange = { texteRecherche = it },
            label = { Text("Rechercher un exercice...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(12.dp)
        )


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.6f
                )
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nouvel Entraînement", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = nomSaisi,
                    onValueChange = { nomSaisi = it },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = poidsSaisi,
                        onValueChange = { poidsSaisi = it },
                        label = { Text("Kg") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = seriesSaisies,
                        onValueChange = { seriesSaisies = it },
                        label = { Text("Séries") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        if (nomSaisi.isNotBlank()) {
                            val numSeries = seriesSaisies.toIntOrNull() ?: 0
                            if (exerciceAEditar == null) {

                                monViewModel.ajouterExercice(
                                    nomSaisi,
                                    "Général",
                                    numSeries,
                                    poidsSaisi,
                                    0
                                )
                            } else {
                                exerciceAEditar?.let {
                                    monViewModel.mettreAJourExercice(
                                        it.copy(
                                            nom = nomSaisi,
                                            series = numSeries,
                                            poids = poidsSaisi
                                        )
                                    )
                                }
                                exerciceAEditar = null
                            }
                            nomSaisi = ""; poidsSaisi = ""; seriesSaisies = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (exerciceAEditar == null) "ENREGISTRER" else "MODIFIER")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        LazyColumn(modifier = Modifier.weight(1f)) {
            items(listaFiltrada) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            exerciceAEditar = item
                            nomSaisi = item.nom
                            poidsSaisi = item.poids
                            seriesSaisies = item.series.toString()
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(item.nom, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("🔥 ${item.series} Séries | ⚖️ ${item.poids} Kg") },
                        trailingContent = {
                            IconButton(onClick = { monViewModel.supprimerExercice(item) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}