package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.MistakeEntity
import com.example.data.model.MistakeAnalysisResponse
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
fun MistakeTrackerScreen(
    viewModel: LakshyaViewModel,
    onNavigateBack: () -> Unit,
    onStartSpacedRepetitionQuiz: (subject: String, topic: String) -> Unit
) {
    val unresolvedMistakes by viewModel.unresolvedMistakes.collectAsState()
    val mistakeAnalysisState by viewModel.mistakeAnalysisState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "ভুল বিশ্লেষণ আৰু ট্ৰেকাৰ (Mistake Diagnostic)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "পুনৰাবৃত্তিমূলক ভুলৰ চিনাক্তকৰণ",
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
                    IconButton(onClick = { viewModel.runMistakeAnalysis() }) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Run Diagnostic",
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
                .testTag("mistake_tracker_content"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Diagnostic Trigger Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LakshyaNavyPrimary)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = null,
                                tint = LakshyaGoldAccent,
                                modifier = Modifier.size(32.dp)
                            )
                            Column {
                                Text(
                                    text = "AI ভুল ধৰা যন্ত্ৰ (Pattern Diagnostic)",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "আপোনাৰ পূৰ্বৰ সকলো ভুলৰ বিশ্লেষণ কৰি পুনৰাবৃত্তিমূলক পেটাৰ্ণ নিৰ্ণয় কৰক",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { viewModel.runMistakeAnalysis() },
                            colors = ButtonDefaults.buttonColors(containerColor = LakshyaGoldAccent),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("run_diagnostic_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color(0xFF78350F)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "পুনৰাবৃত্তিমূলক ভুলৰ বিশ্লেষণ আৰম্ভ কৰক",
                                color = Color(0xFF78350F),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Diagnostic Results
            when (val state = mistakeAnalysisState) {
                is UiState.Loading -> {
                    item {
                        LoadingView(message = "ভুলৰ পেটাৰ্ণ আৰু স্পেচড ৰিপিটিশ্বন অনুক্ৰম বিশ্লেষণ হৈ আছে...")
                    }
                }
                is UiState.Success -> {
                    val analysis = state.data
                    item {
                        MistakePatternAnalysisView(
                            analysis = analysis,
                            onTopicClick = { topic ->
                                onStartSpacedRepetitionQuiz("General", topic)
                            }
                        )
                    }
                }
                is UiState.Error -> {
                    item {
                        Text(text = "বিশ্লেষণ ব্যৰ্থ: ${state.message}", color = LakshyaAssamRed)
                    }
                }
                is UiState.Idle -> {}
            }

            // Unresolved Mistakes Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "সমাধান হ'বলৈ বাকী থকা ভুলবোৰ (${unresolvedMistakes.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (unresolvedMistakes.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4))
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = LakshyaGreenSuccess,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "কোনো অমীমাংসিত ভুল নাই!",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = LakshyaGreenSuccess
                            )
                            Text(
                                text = "কুইজ বা মক টেষ্টত ভুল হ'লে ইয়াত জমা হ'ব আৰু স্পেচড ৰিপিটিশ্বনত আহিব।",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF166534),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            } else {
                items(unresolvedMistakes) { mistake ->
                    MistakeCardItem(
                        mistake = mistake,
                        onMarkResolved = { viewModel.markMistakeResolved(mistake.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MistakePatternAnalysisView(
    analysis: MistakeAnalysisResponse,
    onTopicClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pattern_analysis_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, LakshyaGoldAccent)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "চিনাক্ত কৰা ভুলৰ পেটাৰ্ণসমূহ (Recurring Patterns):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = LakshyaNavyPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            analysis.recurring_patterns.forEach { pattern ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                    border = BorderStroke(1.dp, Color(0xFFFDE68A))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = pattern.pattern,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F),
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                color = LakshyaAssamRed.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "${pattern.frequency} বাৰ ভুল",
                                    color = LakshyaAssamRed,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "💡 পৰামৰ্শ: ${pattern.recommended_action_assamese}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF92400E),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            if (analysis.next_spaced_repetition_topics.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "পৰৱৰ্তী স্পেচড ৰিপিটিশ্বন বিষয়সমূহ (Next Revision Focus):",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = LakshyaNavyPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                analysis.next_spaced_repetition_topics.forEach { topic ->
                    Surface(
                        color = Color(0xFFEFF6FF),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .clickable { onTopicClick(topic) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "• $topic",
                                style = MaterialTheme.typography.bodySmall,
                                color = LakshyaNavyPrimary,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "অনুশীলন কৰক ➔",
                                style = MaterialTheme.typography.labelSmall,
                                color = LakshyaGoldAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MistakeCardItem(
    mistake: MistakeEntity,
    onMarkResolved: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFFFFE4E6),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${mistake.subject} • ${mistake.chapter}",
                        color = LakshyaAssamRed,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                if (mistake.conceptTag.isNotBlank()) {
                    Text(
                        text = mistake.conceptTag,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = mistake.questionAssamese,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "আপোনাৰ ভুল উত্তৰ: ${mistake.userAnswer}",
                style = MaterialTheme.typography.bodySmall,
                color = LakshyaAssamRed
            )
            Text(
                text = "সঠিক উত্তৰ: ${mistake.correctAnswer}",
                style = MaterialTheme.typography.bodySmall,
                color = LakshyaGreenSuccess,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onMarkResolved,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("আয়ত্ত হ'ল (Mark Mastered)", fontSize = 11.sp)
                }
            }
        }
    }
}
