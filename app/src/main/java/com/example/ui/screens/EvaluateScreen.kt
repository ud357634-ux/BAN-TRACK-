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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EvaluateResponse
import com.example.data.model.SebaSubject
import com.example.ui.components.LoadingView
import com.example.ui.theme.LakshyaAssamRed
import com.example.ui.theme.LakshyaGoldAccent
import com.example.ui.theme.LakshyaGoldContainer
import com.example.ui.theme.LakshyaGreenSuccess
import com.example.ui.theme.LakshyaNavyPrimary
import com.example.ui.theme.LakshyaTopperGold
import com.example.ui.viewmodel.LakshyaViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluateScreen(
    viewModel: LakshyaViewModel,
    onNavigateBack: () -> Unit
) {
    val evaluateState by viewModel.evaluateState.collectAsState()

    var selectedSubject by remember { mutableStateOf(SebaSubject.SCIENCE) }
    var questionText by remember {
        mutableStateOf("মানুহৰ হৃদপিণ্ডত তেজৰ দ্বৈত সংবহন (Double Circulation) পদ্ধতিটো কি আৰু ইয়াৰ প্ৰয়োজনীয়তা কি?")
    }
    var studentAnswer by remember {
        mutableStateOf("হৃদপিণ্ডৰ মাজেৰে তেজ দুবাৰ পাৰ হয়। প্ৰথমবাৰ হাওঁফাঁওলৈ যায় আৰু দ্বিতীয়বাৰ সমগ্ৰ শৰীৰলৈ যায়। ইয়াৰ দ্বাৰা বিশুদ্ধ আৰু অশুদ্ধ তেজ পৃথকে থাকে।")
    }
    var totalMarks by remember { mutableStateOf("5") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "উত্তৰ মূল্যাংকন (Answer Evaluator)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "SEBA ৰুব্ৰিকভিত্তিক পৰীক্ষক",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .testTag("evaluate_screen_content"),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "পৰীক্ষাৰ উত্তৰ পৰীক্ষণ ইঞ্জিন (SEBA Marking Rubric)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "আপোনাৰ লিখিত উত্তৰ টাইপ কৰক। লক্ষ্য AI-য়ে SEBA মাৰ্কিং স্কীম অনুসৰি দফাবাৰী নম্বৰ আৰু ০.১% টপাৰ পৰ্যায়ৰ সংশোধন আগবঢ়াব।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }

                // Inputs
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = questionText,
                                onValueChange = { questionText = it },
                                label = { Text("প্ৰশ্নটো (Question Text)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("eval_question_input"),
                                shape = RoundedCornerShape(10.dp),
                                maxLines = 3
                            )

                            OutlinedTextField(
                                value = studentAnswer,
                                onValueChange = { studentAnswer = it },
                                label = { Text("আপোনাৰ উত্তৰ (Your Written Answer)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("eval_answer_input"),
                                shape = RoundedCornerShape(10.dp),
                                minLines = 4,
                                maxLines = 8
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = totalMarks,
                                    onValueChange = { totalMarks = it },
                                    label = { Text("মুঠ নম্বৰ") },
                                    modifier = Modifier.width(100.dp),
                                    shape = RoundedCornerShape(10.dp)
                                )

                                Button(
                                    onClick = {
                                        viewModel.evaluateWrittenAnswer(
                                            subject = selectedSubject.id,
                                            chapter = "General",
                                            questionText = questionText,
                                            studentAnswer = studentAnswer,
                                            totalMarks = totalMarks.toDoubleOrNull() ?: 5.0
                                        )
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary),
                                    modifier = Modifier.testTag("evaluate_submit_button")
                                ) {
                                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("মূল্যাংকন কৰক (Evaluate)")
                                }
                            }
                        }
                    }
                }

                // Evaluation Result Section
                when (val state = evaluateState) {
                    is UiState.Loading -> {
                        item {
                            LoadingView(message = "SEBA পৰীক্ষকৰ দৃষ্টিভংগীৰে উত্তৰৰ প্ৰতিটো শব্দ আৰু ধাৰণা বিশ্লেষণ কৰা হৈছে...")
                        }
                    }
                    is UiState.Error -> {
                        item {
                            Text(text = "মূল্যাংকন ব্যৰ্থ: ${state.message}", color = LakshyaAssamRed)
                        }
                    }
                    is UiState.Success -> {
                        val result = state.data
                        item {
                            EvaluationResultCard(result = result)
                        }
                    }
                    is UiState.Idle -> {
                        // Show past sample or prompt
                    }
                }
            }
        }
    }
}

@Composable
fun EvaluationResultCard(result: EvaluateResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("evaluation_result_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, LakshyaGoldAccent)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "মূল্যাংকন ফলাফল (Evaluation Scorecard)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = LakshyaNavyPrimary
                    )
                    if (result.mistake_pattern_tag.isNotBlank()) {
                        Surface(
                            color = Color(0xFFFEF3C7),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                text = "পাৰ্ট পেটাৰ্ণ: ${result.mistake_pattern_tag}",
                                color = Color(0xFF92400E),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Surface(
                    color = LakshyaNavyPrimary,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${result.marks_awarded} / ${result.marks_total}",
                            color = LakshyaTopperGold,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Marks Awarded",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 9.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Rubric Breakdown
            Text(
                text = "দফাবাৰী নম্বৰ বিভাজন (Rubric Breakdown):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            result.rubric_breakdown.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item.criterion,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                color = Color(0xFFDCFCE7),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "+${item.marks} M",
                                    color = LakshyaGreenSuccess,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.feedback_assamese,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF475569),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Top 0.1% Improvement Tip
            if (result.improvement_tip_assamese.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LakshyaGoldContainer, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Color(0xFF78350F),
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "শীৰ্ষ ০.১% টপাৰ পৰামৰ্শ (Top 0.1% Tip):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = result.improvement_tip_assamese,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF451A03),
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
