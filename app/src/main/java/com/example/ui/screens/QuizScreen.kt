package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuestionItem
import com.example.data.model.QuizResponse
import com.example.ui.components.ConfidenceBadge
import com.example.ui.components.LoadingView
import com.example.ui.theme.LakshyaAssamRed
import com.example.ui.theme.LakshyaGoldAccent
import com.example.ui.theme.LakshyaGoldContainer
import com.example.ui.theme.LakshyaGreenLight
import com.example.ui.theme.LakshyaGreenSuccess
import com.example.ui.theme.LakshyaNavyPrimary
import com.example.ui.theme.LakshyaTopperGold
import com.example.ui.viewmodel.LakshyaViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: LakshyaViewModel,
    subject: String,
    chapter: String,
    onNavigateBack: () -> Unit
) {
    val quizState by viewModel.quizState.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val isSubmitted by viewModel.quizSubmitted.collectAsState()
    val totalScore by viewModel.quizScore.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "অনুশীলন কুইজ (Quiz)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "$subject • $chapter",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1),
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("quiz_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.startQuiz(subject, chapter) },
                        modifier = Modifier.testTag("quiz_reload_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "New Quiz",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LakshyaNavyPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (val state = quizState) {
                is UiState.Loading -> {
                    LoadingView(message = "SEBA $subject ৰ পৰীক্ষাভিত্তিক প্ৰশ্ন আৰু সমাধান প্ৰস্তুত হৈছে...")
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "প্ৰশ্ন লোড কৰাত অসুবিধা হৈছে",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = LakshyaAssamRed
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.startQuiz(subject, chapter) },
                            colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary)
                        ) {
                            Text("পুনৰ চেষ্টা কৰক (Retry)")
                        }
                    }
                }
                is UiState.Success -> {
                    val quiz = state.data
                    if (quiz.questions.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("কোনো প্ৰশ্ন উপলব্ধ নহয়।")
                        }
                    } else if (isSubmitted) {
                        QuizResultSummaryView(
                            quiz = quiz,
                            selectedAnswers = selectedAnswers,
                            score = totalScore,
                            onRetry = { viewModel.startQuiz(subject, chapter) },
                            onBackToHome = onNavigateBack
                        )
                    } else {
                        QuizPlayerView(
                            quiz = quiz,
                            currentIndex = currentIndex,
                            selectedAnswers = selectedAnswers,
                            onSelectAnswer = { ans -> viewModel.selectQuizAnswer(currentIndex, ans) },
                            onNext = { viewModel.nextQuestion(quiz.questions.size) },
                            onPrevious = { viewModel.previousQuestion() },
                            onSubmit = { viewModel.submitQuiz() }
                        )
                    }
                }
                is UiState.Idle -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = { viewModel.startQuiz(subject, chapter) },
                            colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary)
                        ) {
                            Text("কুইজ আৰম্ভ কৰক")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizPlayerView(
    quiz: QuizResponse,
    currentIndex: Int,
    selectedAnswers: Map<Int, String>,
    onSelectAnswer: (String) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSubmit: () -> Unit
) {
    val totalQuestions = quiz.questions.size
    val currentQuestion = quiz.questions[currentIndex]
    val selectedAnswer = selectedAnswers[currentIndex].orEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("quiz_player_view"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top status and progress
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = LakshyaGoldContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "প্ৰশ্ন ${currentIndex + 1} / $totalQuestions",
                        color = Color(0xFF78350F),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = Color(0xFFDBEAFE),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "মান: ${currentQuestion.marks} Marks",
                        color = Color(0xFF1E3A8A),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / totalQuestions },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = LakshyaGoldAccent,
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Question Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("question_card_${currentIndex}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ConfidenceBadge(
                        confidence = currentQuestion.confidence_flag,
                        verifyNote = currentQuestion.verify_note
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = currentQuestion.question_assamese,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 26.sp
                    )

                    if (currentQuestion.question_english_terms.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            currentQuestion.question_english_terms.forEach { term ->
                                Surface(
                                    color = Color(0xFFF1F5F9),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = term,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF475569),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Options or input depending on question type
            if (currentQuestion.type == "MCQ" || currentQuestion.type == "true_false") {
                val opts = if (currentQuestion.options.isNotEmpty()) currentQuestion.options else listOf("A", "B", "C", "D")
                opts.forEach { option ->
                    val isSelected = selectedAnswer == option
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onSelectAnswer(option) }
                            .testTag("option_${option.take(3)}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFFEFF6FF) else MaterialTheme.colorScheme.surface
                        ),
                        border = BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) LakshyaNavyPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        if (isSelected) LakshyaNavyPrimary else Color.Transparent,
                                        CircleShape
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isSelected) LakshyaNavyPrimary else Color(0xFF94A3B8),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) LakshyaNavyPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else {
                // Short answer / one word text field
                OutlinedTextField(
                    value = selectedAnswer,
                    onValueChange = onSelectAnswer,
                    label = { Text("আপোনাৰ উত্তৰ ইয়াত লিখক (Your Answer)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("short_answer_input"),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Bottom Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onPrevious,
                enabled = currentIndex > 0,
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("পূৰ্বৱৰ্তী")
            }

            if (currentIndex == totalQuestions - 1) {
                Button(
                    onClick = onSubmit,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LakshyaGreenSuccess),
                    modifier = Modifier.testTag("submit_quiz_button")
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("কুইজ সমাপ্ত আৰু ফলাফল", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary),
                    modifier = Modifier.testTag("next_question_button")
                ) {
                    Text("পৰৱৰ্তী")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next"
                    )
                }
            }
        }
    }
}

@Composable
fun QuizResultSummaryView(
    quiz: QuizResponse,
    selectedAnswers: Map<Int, String>,
    score: Int,
    onRetry: () -> Unit,
    onBackToHome: () -> Unit
) {
    val totalMarks = quiz.questions.sumOf { it.marks }
    val percentage = if (totalMarks > 0) (score.toFloat() / totalMarks * 100).toInt() else 0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("quiz_result_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = LakshyaNavyPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = LakshyaTopperGold,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (percentage >= 80) "অসাধাৰণ! শীৰ্ষ ০.১% টপাৰ পৰ্যায়" else "ভাল প্ৰচেষ্টা! ভুলবোৰ পুনৰীক্ষণ কৰক",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "প্ৰাপ্ত নম্বৰ: $score / $totalMarks ($percentage%)",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = LakshyaTopperGold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "ভুল হোৱা প্ৰশ্নসমূহ স্বয়ংক্ৰিয়ভাৱে 'ভুল বিশ্লেষণ (Mistake Tracker)' ত যোগ কৰা হৈছে।",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1),
                        fontSize = 11.sp
                    )
                }
            }
        }

        item {
            Text(
                text = "পদক্ষেপসহ উত্তৰ আৰু সমাধান (Step-by-step Solutions):",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        itemsIndexed(quiz.questions) { index, question ->
            var expanded by remember { mutableStateOf(false) }
            val userAns = selectedAnswers[index].orEmpty()
            val isCorrect = userAns.isNotBlank() && (
                    userAns.startsWith(question.correct_answer.take(2), ignoreCase = true) ||
                            userAns.equals(question.correct_answer, ignoreCase = true)
                    )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(
                    1.dp,
                    if (isCorrect) LakshyaGreenSuccess.copy(alpha = 0.5f) else LakshyaAssamRed.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = if (isCorrect) LakshyaGreenLight else Color(0xFFFFE4E6),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = if (isCorrect) "শুদ্ধ (+${question.marks}M)" else "ভুল (0M)",
                                color = if (isCorrect) LakshyaGreenSuccess else LakshyaAssamRed,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = question.concept_tag,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${index + 1}. ${question.question_assamese}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "আপোনাৰ উত্তৰ: ${if (userAns.isNotBlank()) userAns else "উত্তৰ দিয়া নহ'ল"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isCorrect) LakshyaGreenSuccess else LakshyaAssamRed
                    )
                    Text(
                        text = "সঠিক উত্তৰ: ${question.correct_answer}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = LakshyaNavyPrimary
                    )

                    // Expandable Solution Steps
                    if (question.solution_steps.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expanded = !expanded }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (expanded) "সমাধান লুকুৱাওক" else "বিশ্লেষণ আৰু সমাধান চাওক (Solution Steps)",
                                style = MaterialTheme.typography.labelMedium,
                                color = LakshyaGoldAccent,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = LakshyaGoldAccent
                            )
                        }

                        AnimatedVisibility(visible = expanded) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
                                    .padding(10.dp)
                            ) {
                                question.solution_steps.forEachIndexed { sIdx, step ->
                                    Text(
                                        text = "• $step",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF334155),
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("পুনৰ কুইজ দিয়ক")
                }
                Button(
                    onClick = onBackToHome,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("গৃহলৈ উভতক (Home)")
                }
            }
        }
    }
}
