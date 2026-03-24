FitnessApp

# Thème de l'application : Performance & Hypertrophie

FitnessApp est un carnet d'entraînement numérique conçu pour maximiser la croissance musculaire et
le gain de force. L'objectif principal est de permettre aux pratiquants de musculation de piloter
leur surcharge progressive de manière rigoureuse.

En consignant précisément la charge (Kg) et le volume (Séries) pour chaque exercice, l'utilisateur
peut s'assurer qu'il gagne en force séance après séance, condition indispensable pour
l'hypertrophie. L'application offre un contrôle total sur l'historique de performance pour éviter
toute stagnation.

# Fonctionnalités CRUD (Gestion Complète)

L'application permet une gestion dynamique de votre bibliothèque d'exercices :
Créer : Ajoutez de nouveaux exercices avec des détails spécifiques (Nom, Muscle, Poids, Séries).
Lire : Visualisez votre liste d'exercices en temps réel avec un système de recherche intégré.
Modifier : Mettez à jour vos records (Poids/Séries) pour refléter votre progression actuelle.
Supprimer : Nettoyez votre liste en retirant les exercices qui ne font plus partie de votre
programme.

# Guide d'utilisation (Comment utiliser l'app)

1. Ajout d'un exercice : Remplissez les champs du formulaire "Nouvel Entraînement". Saisissez le
   nom, le muscle cible, le poids utilisé et le nombre de séries, puis cliquez sur ENREGISTRER.
2. Recherche : Utilisez la barre "Rechercher un exercice..." en haut de l'écran pour retrouver
   instantanément un mouvement spécifique dans votre liste.
3. Édition : Pour modifier une valeur (par exemple, si vous avez augmenté votre poids de 5kg),
   cliquez sur l'icône d'édition de l'exercice concerné, ajustez les données et validez.
4. Suppression : Si vous souhaitez retirer un exercice, cliquez sur l'icône de la corbeille pour le
   supprimer définitivement de la base de données.
5. Mode Sombre/Clair : Utilisez l'interrupteur (Switch) en haut à droite pour adapter l'interface à
   votre environnement d'entraînement.

# Choix de la base de données

Le projet utilise Room Database pour la persistance des données.
Room permet une gestion locale robuste via SQLite, assurant que les données de l'utilisateur restent
disponibles hors ligne avec une intégration native des Coroutines Kotlin pour des performances
optimales.

# Comment lancer le projet

1. Ouvrez le projet dans Android Studio (Version Ladybug ou ultérieure).
2. Laissez Gradle synchroniser les dépendances.
3. Lancez l'émulateur (API 31 ou plus recommandé).
4. Cliquez sur le bouton Run (exécuter).
   Note importante : L'application démarre avec une base de données vide pour permettre au
   correcteur de tester l'intégralité du flux d'ajout et de validation des données.

# Architecture et Structure du Code

Le projet respecte l'architecture MVVM (Model-View-ViewModel)

Data Source / DAO : Situé dans `data/ExerciceDao.kt`. Contient les requêtes SQL (Insert, Query,
Delete).
Repository : Situé dans `data/ExerciceRepository.kt`. Gère la communication entre la source de
données et la logique métier.
ViewModel : Situé dans `ui/ExerciceViewModel.kt`. Gère l'état de l'interface (UI State) et le
filtrage en temps réel pour la recherche.

# Valeurs stockées dans DataStore

L'application utilise Preferences DataStore pour sauvegarder les préférences de l'utilisateur :
`is_dark_mode` : Stocke un booléen pour mémoriser si l'utilisateur a activé le mode sombre ou le
mode clair, même après le redémarrage de l'application.

# Comment lancer les tests

Des tests d'instrumentation ont été inclus pour vérifier l'intégrité de la base de données :

1. Allez dans le dossier `app/src/androidTest`.
2. Faites un clic droit sur la classe de test (ex: `ExerciceDatabaseTest.kt`).
3. Sélectionnez "Run...".
4. Les résultats s'afficheront dans l'onglet "Test Results" d'Android Studio.