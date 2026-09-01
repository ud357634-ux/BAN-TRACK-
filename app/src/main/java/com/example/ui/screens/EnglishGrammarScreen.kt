package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.data.model.EnglishGrammarResponse
import com.example.data.model.QuestionItem
import com.example.ui.components.ConfidenceBadge
import com.example.ui.components.LoadingView
import com.example.ui.theme.LakshyaAssamRed
import com.example.ui.theme.LakshyaGoldAccent
import com.example.ui.theme.LakshyaGoldContainer
import com.example.ui.theme.LakshyaGreenSuccess
import com.example.ui.theme.LakshyaNavyPrimary
import com.example.ui.viewmodel.LakshyaViewModel
import com.example.ui.viewmodel.UiState

val SEBA_GRAMMAR_TOPICS = listOf(
    "Correct Tenses",
    "Voice Change",
    "Narration (Direct/Indirect)",
    "Appropriate Prepositions",
    "Subject-Verb Concord",
    "Determiners & Articles",
    "Synthesis of Sentences",
    "Question Tags",
    "Phrasal Verbs & Idioms"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnglishGrammarScreen(
    viewModel: LakshyaViewModel,
    onNavigateBack: () -> Unit
) {
    val grammarState by viewModel.grammarState.collectAsState()
    var selectedTopic by remember { mutableStateOf("Correct Tenses") }

    LaunchedEffect(Unit) {
        viewModel.loadGrammarTopic(selectedTopic)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "SEBA ইংৰাজী ব্যাকৰণ (English Grammar)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Class 10 Board High-Yield Topics",
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
                actions = {
                    IconButton(onClick = { viewModel.loadGrammarTopic(selectedTopic) }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reload",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LakshyaNavyPrimary)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Horizontal topic pills
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(SEBA_GRAMMAR_TOPICS) { topic ->
                    FilterChip(
                        selected = selectedTopic == topic,
                        onClick = {
                            selectedTopic = topic
                            viewModel.loadGrammarTopic(topic)
                        },
                        label = {
                            Text(
                                text = topic,
                                fontWeight = if (selectedTopic == topic) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LakshyaNavyPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = grammarState) {
                    is UiState.Loading -> {
                        LoadingView(message = "SEBA $selectedTopic ৰ নিয়ম আৰু নমুনা প্ৰশ্ন প্ৰস্তুত হৈছে...")
                    }
                    is UiState.Error -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "লোড নহ'ল: ${state.message}", color = LakshyaAssamRed)
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = { viewModel.loadGrammarTopic(selectedTopic) }) {
                                Text("পুনৰ চেষ্টা")
                            }
                        }
                    }
                    is UiState.Success -> {
                        GrammarContentView(grammar = state.data)
                    }
                    is UiState.Idle -> {}
                }
            }
        }
    }
}

@Composable
fun GrammarContentView(grammar: EnglishGrammarResponse) {
    var selectedAnswers by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
    var showSolutions by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("grammar_content_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Rule Explanation Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, LakshyaNavyPrimary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = LakshyaNavyPrimary
                        )
                        Text(
                            text = "প্ৰয়োজনীয় নিয়ম আৰু সূত্ৰ (${grammar.grammar_topic})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = LakshyaNavyPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = grammar.rule_explanation_english,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Examples
        if (grammar.examples.isNotEmpty()) {
            item {
                Text(
                    text = "SEBA আৰ্হি ৰূপান্তৰ আৰু উদাহৰণ (Model Transformations):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            items(grammar.examples) { example ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = example.sentence,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "➔ ${example.correction_or_transformation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = LakshyaNavyPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Practice questions
        if (grammar.practice_questions.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "অনশীলন প্ৰশ্নসমূহ (${grammar.practice_questions.size}):",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedButton(
                        onClick = { showSolutions = !showSolutions },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (showSolutions) "উত্তৰ লুকুৱাওক" else "উত্তৰ পৰীক্ষা কৰক", fontSize = 11.sp)
                    }
                }
            }

            itemsIndexed(grammar.practice_questions) { index, question ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        ConfidenceBadge(confidence = question.confidence_flag, verifyNote = question.verify_note)
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${index + 1}. ${question.question_assamese}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )

                        if (question.options.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            question.options.forEach { opt ->
                                val isSelected = selectedAnswers[index] == opt
                                Surface(
                                    color = if (isSelected) Color(0xFFEFF6FF) else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isSelected) LakshyaNavyPrimary else Color(0xFFE2E8F0)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp)
                                        .clickable {
                                            selectedAnswers = selectedAnswers.toMutableMap().apply {
                                                put(index, opt)
                                            }
                                        }
                                ) {
                                    Text(
                                        text = opt,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isSelected) LakshyaNavyPrimary else MaterialTheme.colorScheme.onSurface,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }

                        if (showSolutions) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF0FDF4), RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "সঠিক উত্তৰ: ${question.correct_answer}",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LakshyaGreenSuccess
                                )
                                question.solution_steps.forEach { step ->
                                    Text(
                                        text = "• $step",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF166534),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
