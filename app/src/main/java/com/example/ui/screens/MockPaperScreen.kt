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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MockPaperResponse
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockPaperScreen(
    viewModel: LakshyaViewModel,
    subject: String,
    onNavigateBack: () -> Unit
) {
    val mockPaperState by viewModel.mockPaperState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "SEBA সম্পূৰ্ণ প্ৰশ্নকাকত (Mock Paper)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "$subject • Full Board Pattern",
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
                    IconButton(onClick = { viewModel.startMockPaper(subject) }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Regenerate",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LakshyaNavyPrimary)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (val state = mockPaperState) {
                is UiState.Loading -> {
                    LoadingView(message = "SEBA আধিকাৰিক পেটাৰ্ণ (Group A, B, C & OR) অনুসৰি পূৰ্ণাঙ্গ কাকত প্ৰস্তুত হৈছে...")
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "প্ৰশ্নকাকত লোড নহ'ল: ${state.message}", color = LakshyaAssamRed)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.startMockPaper(subject) }) {
                            Text("পুনৰ চেষ্টা কৰক")
                        }
                    }
                }
                is UiState.Success -> {
                    MockPaperContentView(paper = state.data)
                }
                is UiState.Idle -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = { viewModel.startMockPaper(subject) },
                            colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary)
                        ) {
                            Text("SEBA পূৰ্ণাঙ্গ প্ৰশ্নকাকত সৃষ্টি কৰক (${subject})")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MockPaperContentView(paper: MockPaperResponse) {
    var showAnswers by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("mock_paper_content_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Board Header Frame
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.5.dp, LakshyaNavyPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "BOARD OF SECONDARY EDUCATION, ASSAM (SEBA)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "HIGH SCHOOL LEAVING CERTIFICATE (HSLC) EXAMINATION",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = LakshyaNavyPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "বিষয়: ${paper.subject} (Subject: ${paper.subject})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = LakshyaGoldAccent
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "পূৰ্ণ মান (Full Marks): ${paper.full_marks}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "সময় (Time): ${paper.time_hours} Hours",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (paper.pattern_note.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "📌 ${paper.pattern_note}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Toggle answer key
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ConfidenceBadge(
                    confidence = paper.pattern_confidence,
                    verifyNote = "SEBA অফিচিয়েল গ্ৰুপ বিভাজন"
                )

                OutlinedButton(
                    onClick = { showAnswers = !showAnswers },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(if (showAnswers) "উত্তৰ লুকুৱাওক" else "সমাধান আৰু উত্তৰ চাওক", fontSize = 12.sp)
                }
            }
        }

        // Groups
        paper.groups.forEach { group ->
            item {
                Surface(
                    color = Color(0xFF1E293B),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = group.group_name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (group.instructions_assamese.isNotBlank()) {
                            Text(
                                text = group.instructions_assamese,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            itemsIndexed(group.questions) { qIdx, question ->
                PaperQuestionCard(
                    number = "${qIdx + 1}",
                    question = question,
                    showAnswer = showAnswers
                )
            }
        }
    }
}

@Composable
fun PaperQuestionCard(
    number: String,
    question: QuestionItem,
    showAnswer: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "$number. ${question.question_assamese}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    color = Color(0xFFF1F5F9),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "[${question.marks}]",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            if (question.options.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                question.options.forEach { opt ->
                    Text(
                        text = opt,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF475569),
                        modifier = Modifier.padding(start = 12.dp, top = 2.dp)
                    )
                }
            }

            // Internal OR Choice question (as per SEBA Group C)
            if (question.or_question_assamese != null && question.or_question_assamese.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFEF3C7).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "অথবা (OR)",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = question.or_question_assamese,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF78350F)
                        )
                    }
                }
            }

            if (showAnswer) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0FDF4), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "মডেল উত্তৰ: ${question.correct_answer}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = LakshyaGreenSuccess
                    )
                    if (question.solution_steps.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
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
