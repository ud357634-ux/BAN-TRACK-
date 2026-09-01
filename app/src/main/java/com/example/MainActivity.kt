package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.EnglishGrammarScreen
import com.example.ui.screens.EvaluateScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MistakeTrackerScreen
import com.example.ui.screens.MockPaperScreen
import com.example.ui.screens.MockTestScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SettingsDialog
import com.example.ui.screens.SourceMaterialDialog
import com.example.ui.screens.VideoQuizScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LakshyaViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LakshyaApp()
                }
            }
        }
    }
}

@Composable
fun LakshyaApp(
    viewModel: LakshyaViewModel = viewModel()
) {
    val navController = rememberNavController()

    var showSourceMaterialDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val sourceMaterial by viewModel.sourceMaterial.collectAsState()
    val customApiKey by viewModel.customApiKey.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToQuiz = { subject, chapter ->
                    viewModel.startQuiz(subject, chapter)
                    val encSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString())
                    val encChapter = URLEncoder.encode(chapter, StandardCharsets.UTF_8.toString())
                    navController.navigate("quiz/$encSubject/$encChapter")
                },
                onNavigateToMockTest = { subject ->
                    viewModel.startMockTest(subject)
                    val encSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString())
                    navController.navigate("mock_test/$encSubject")
                },
                onNavigateToMockPaper = { subject ->
                    viewModel.startMockPaper(subject)
                    val encSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString())
                    navController.navigate("mock_paper/$encSubject")
                },
                onNavigateToEvaluate = {
                    viewModel.resetEvaluate()
                    navController.navigate("evaluate")
                },
                onNavigateToMistakes = {
                    navController.navigate("mistakes")
                },
                onNavigateToGrammar = {
                    navController.navigate("grammar")
                },
                onNavigateToVideoQuiz = {
                    navController.navigate("video_quiz")
                },
                onOpenSourceMaterial = { showSourceMaterialDialog = true },
                onOpenSettings = { showSettingsDialog = true }
            )
        }

        composable(
            route = "quiz/{subject}/{chapter}",
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType },
                navArgument("chapter") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawSubject = backStackEntry.arguments?.getString("subject").orEmpty()
            val rawChapter = backStackEntry.arguments?.getString("chapter").orEmpty()
            val subject = URLDecoder.decode(rawSubject, StandardCharsets.UTF_8.toString())
            val chapter = URLDecoder.decode(rawChapter, StandardCharsets.UTF_8.toString())

            QuizScreen(
                viewModel = viewModel,
                subject = subject,
                chapter = chapter,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "mock_test/{subject}",
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawSubject = backStackEntry.arguments?.getString("subject").orEmpty()
            val subject = URLDecoder.decode(rawSubject, StandardCharsets.UTF_8.toString())

            MockTestScreen(
                viewModel = viewModel,
                subject = subject,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "mock_paper/{subject}",
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawSubject = backStackEntry.arguments?.getString("subject").orEmpty()
            val subject = URLDecoder.decode(rawSubject, StandardCharsets.UTF_8.toString())

            MockPaperScreen(
                viewModel = viewModel,
                subject = subject,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("evaluate") {
            EvaluateScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("mistakes") {
            MistakeTrackerScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onStartSpacedRepetitionQuiz = { subject, topic ->
                    viewModel.startQuiz(subject, topic)
                    val encSubject = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString())
                    val encTopic = URLEncoder.encode(topic, StandardCharsets.UTF_8.toString())
                    navController.navigate("quiz/$encSubject/$encTopic")
                }
            )
        }

        composable("grammar") {
            EnglishGrammarScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("video_quiz") {
            VideoQuizScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }

    if (showSourceMaterialDialog) {
        SourceMaterialDialog(
            initialMaterial = sourceMaterial,
            onSave = { viewModel.setSourceMaterial(it) },
            onDismiss = { showSourceMaterialDialog = false }
        )
    }

    if (showSettingsDialog) {
        SettingsDialog(
            currentApiKey = customApiKey,
            onSaveApiKey = { viewModel.setCustomApiKey(it) },
            onDismiss = { showSettingsDialog = false }
        )
    }
}
