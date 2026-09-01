package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizResponse
import com.example.data.model.SebaSubject
import com.example.ui.components.LoadingView
import com.example.ui.theme.LakshyaAssamRed
import com.example.ui.theme.LakshyaGoldAccent
import com.example.ui.theme.LakshyaNavyPrimary
import com.example.ui.viewmodel.LakshyaViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoQuizScreen(
    viewModel: LakshyaViewModel,
    onNavigateBack: () -> Unit
) {
    val videoQuizState by viewModel.videoQuizState.collectAsState()

    var videoTitle by remember { mutableStateOf("SEBA Class 10 Science - Chemical Reactions & Equations Revision") }
    var videoTranscript by remember {
        mutableStateOf(
            """
            In today's SEBA Class 10 revision lecture, we study Chemical Reactions and Equations. 
            When Magnesium ribbon burns in oxygen with a dazzling white flame, it produces white powder of Magnesium Oxide (2Mg + O2 -> 2MgO). 
            Next, when lime water reacts with CO2 gas, Calcium Carbonate precipitate forms, turning it milky. 
            We also reviewed displacement reactions where Zinc replaces Copper from Copper Sulphate solution (Zn + CuSO4 -> ZnSO4 + Cu) because Zinc is more reactive than Copper in the reactivity series.
            """.trimIndent()
        )
    }

    var activeQuiz by remember { mutableStateOf<QuizResponse?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "YouTube ভিডিঅ' আধাৰিত কুইজ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Zero Hallucination Transcript Grounding",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1),
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LakshyaNavyPrimary)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .testTag("video_quiz_content"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = null,
                                tint = LakshyaAssamRed
                            )
                            Text(
                                text = "ভিডিঅ' ট্ৰান্সক্ৰিপ্টৰ পৰা প্ৰশ্ন প্ৰস্তুত কৰক",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = LakshyaNavyPrimary
                            )
                        }

                        Text(
                            text = "যিকোনো YouTube ক্লাস বা শিক্ষামূলক ভিডিঅ'ৰ ট্ৰান্সক্ৰিপ্ট ইয়াত পেষ্ট কৰক। লক্ষ্য AI-য়ে কেৱল ভিডিঅ'টোত উল্লেখ থকা তথ্যসমূহৰ ওপৰত ভিত্তি কৰি প্ৰশ্ন প্ৰস্তুত কৰিব (No Guesswork)।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )

                        OutlinedTextField(
                            value = videoTitle,
                            onValueChange = { videoTitle = it },
                            label = { Text("ভিডিঅ'ৰ শিৰোনাম (Video Title)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = videoTranscript,
                            onValueChange = { videoTranscript = it },
                            label = { Text("ভিডিঅ' ট্ৰান্সক্ৰিপ্ট / ক্লাছৰ সাৰাংশ (Transcript / Notes)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            minLines = 5,
                            maxLines = 10
                        )

                        Button(
                            onClick = {
                                viewModel.generateVideoQuiz(
                                    subject = "Science",
                                    chapter = "Video Notes",
                                    transcript = videoTranscript,
                                    title = videoTitle
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("generate_video_quiz_btn")
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ট্ৰান্সক্ৰিপ্ট কুইজ সৃষ্টি কৰক")
                        }
                    }
                }
            }

            when (val state = videoQuizState) {
                is UiState.Loading -> {
                    item {
                        LoadingView(message = "ট্ৰান্সক্ৰিপ্টৰ প্ৰতিটো তথ্য পৰীক্ষা কৰি প্ৰশ্ন প্ৰস্তুত কৰা হৈছে (Zero Hallucination)...")
                    }
                }
                is UiState.Error -> {
                    item {
                        Text(text = "ব্যৰ্থ: ${state.message}", color = LakshyaAssamRed)
                    }
                }
                is UiState.Success -> {
                    val quiz = state.data
                    item {
                        Text(
                            text = "প্ৰস্তুত কৰা কুইজ (${quiz.questions.size} টা প্ৰশ্ন):",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(quiz.questions.size) { idx ->
                        val question = quiz.questions[idx]
                        MockQuestionItemCard(index = idx + 1, question = question, showSolution = true)
                    }
                }
                is UiState.Idle -> {}
            }
        }
    }
}
