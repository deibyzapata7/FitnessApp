package com.example.fitnessapp


import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import android.app.Activity
import androidx.compose.runtime.SideEffect


data class Exercice(
    val id: Int,
    val title: String,
    val subtitle: String,
    val image: Int
)


val listeExercices = listOf(
    Exercice(1, "Squats", "Muscle : Quadriceps", R.drawable.sentadilla),
    Exercice(2, "Développé Couché", "Muscle : Pectoraux", R.drawable.pressbanca),
    Exercice(3, "Soulevé de Terre", "Muscle : Dos", R.drawable.pesomuerto),
    Exercice(4, "Tractions", "Muscle : Dorsaux", R.drawable.dominadas),
    Exercice(5, "Développé Militaire", "Muscle : Épaules", R.drawable.pressmilitar),
    Exercice(6, "Fentes", "Muscle : Fessiers", R.drawable.zancadas)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            var isDarkMode by remember { mutableStateOf(false) }


            val view = LocalView.current

            SideEffect {

                val window = (view.context as Activity).window

                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkMode
            }

            MaterialTheme(

                colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

            ) {

                Surface(

                    modifier = Modifier.fillMaxSize(),

                    color = MaterialTheme.colorScheme.background
                ) {

                    FitnessAppScreen(isDarkMode, onThemeChange = { isDarkMode = it })

                }
            }
        }
    }
}

@Composable

fun FitnessAppScreen(isDarkMode: Boolean, onThemeChange: (Boolean) -> Unit) {

    var seriesCount by remember { mutableIntStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "FitnessApp",
                style = MaterialTheme.typography.headlineMedium
            )


            Switch(checked = isDarkMode, onCheckedChange = onThemeChange)
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            Button(onClick = { seriesCount++ }) {
                Text("Séries : $seriesCount")
            }

            Button(onClick = { seriesCount = 0 }) {
                Text("Réinitialiser")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Liste d'exercices",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listeExercices) { ejercicio ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = ejercicio.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentScale = ContentScale.FillWidth
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = ejercicio.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = ejercicio.subtitle,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}