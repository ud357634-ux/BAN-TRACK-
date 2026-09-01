package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.SebaSubject
import com.example.ui.theme.LakshyaAssamRed
import com.example.ui.theme.LakshyaBlueContainer
import com.example.ui.theme.LakshyaGoldAccent
import com.example.ui.theme.LakshyaGoldContainer
import com.example.ui.theme.LakshyaGreenSuccess
import com.example.ui.theme.LakshyaNavyPrimary
import com.example.ui.theme.LakshyaNavySecondary
import com.example.ui.theme.LakshyaTopperGold
import com.example.ui.viewmodel.LakshyaViewModel

@Composable
fun HomeScreen(
    viewModel: LakshyaViewModel,
    onNavigateToQuiz: (subject: String, chapter: String) -> Unit,
    onNavigateToMockTest: (subject: String) -> Unit,
    onNavigateToMockPaper: (subject: String) -> Unit,
    onNavigateToEvaluate: () -> Unit,
    onNavigateToMistakes: () -> Unit,
    onNavigateToGrammar: () -> Unit,
    onNavigateToVideoQuiz: () -> Unit,
    onOpenSourceMaterial: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val weakTopics by viewModel.weakTopics.collectAsState()
    val testSessions by viewModel.testSessions.collectAsState()
    val unresolvedMistakes by viewModel.unresolvedMistakes.collectAsState()
    val sourceMaterial by viewModel.sourceMaterial.collectAsState()
    val dueReviewCount by viewModel.dueReviewCount.collectAsState()
    val dueReviews by viewModel.dueReviews.collectAsState()

    var selectedSubject by remember { mutableStateOf(SebaSubject.MATHEMATICS) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen_content"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // 1. Hero Banner
        item {
            HeroHeader(
                onOpenSettings = onOpenSettings,
                onOpenSourceMaterial = onOpenSourceMaterial,
                hasSourceMaterial = sourceMaterial.isNotBlank()
            )
        }

        // 2. Top 0.1% Metric & Target Dashboard
        item {
            TargetMetricCard(
                totalTests = testSessions.size,
                unresolvedCount = unresolvedMistakes.size,
                weakTopicsCount = weakTopics.size
            )
        }

        // 2b. Spaced Repetition Due Review Alert Card (SM-2 Algorithm)
        if (dueReviewCount > 0) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = LakshyaGoldContainer.copy(alpha = 0.35f)
                    ),
                    border = BorderStroke(1.dp, LakshyaGoldAccent.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(LakshyaNavyPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = LakshyaTopperGold,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "$dueReviewCount টি বিষয় পুনৰাবৃত্তিৰ সময় হৈছে (Spaced Review Due)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = LakshyaNavyPrimary
                                )
                                Text(
                                    text = "SM-2 বৈজ্ঞানিক এলগৰিদম অনুসাৰে আজিৰ অনুশীলন সম্পূৰ্ণ কৰক",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = {
                                val firstChapter = selectedSubject.chapters.firstOrNull()?.first ?: "All"
                                onNavigateToQuiz(selectedSubject.id, firstChapter)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LakshyaNavyPrimary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("আৰম্ভ কৰক", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // 3. Quick Mode Launchers
        item {
            Text(
                text = "অনুশীলন আৰু পৰীক্ষা ম'ড (SEBA Prep Modes)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            ModeLauncherGrid(
                onQuizClick = {
                    val firstChapter = selectedSubject.chapters.firstOrNull()?.first ?: "All"
                    onNavigateToQuiz(selectedSubject.id, firstChapter)
                },
                onMockTestClick = { onNavigateToMockTest(selectedSubject.id) },
                onMockPaperClick = { onNavigateToMockPaper(selectedSubject.id) },
                onEvaluateClick = onNavigateToEvaluate,
                onMistakesClick = onNavigateToMistakes,
                onGrammarClick = onNavigateToGrammar,
                onVideoClick = onNavigateToVideoQuiz
            )
        }

        // 4. Subject & Chapter Explorer
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "বিষয় আৰু অধ্যায়ভিত্তিক অনুশীলন (Chapter Practice)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Subject Selector Pills
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(SebaSubject.values()) { subject ->
                    FilterChip(
                        selected = selectedSubject == subject,
                        onClick = { selectedSubject = subject },
                        label = {
                            Text(
                                text = "${subject.titleAssamese} (${subject.titleEnglish})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (selectedSubject == subject) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = LakshyaNavyPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Chapter List for Selected Subject
        items(selectedSubject.chapters) { (chEng, chAsm) ->
            ChapterItemCard(
                subjectTitle = selectedSubject.id,
                chapterEnglish = chEng,
                chapterAssamese = chAsm,
                onStartQuiz = { onNavigateToQuiz(selectedSubject.id, chEng) }
            )
        }

        // 5. Spaced Repetition Weak-Topics Radar
        if (weakTopics.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                WeakTopicsRadarCard(
                    weakTopics = weakTopics.take(4),
                    onReviseTopic = { topic ->
                        onNavigateToQuiz(selectedSubject.id, topic.topic)
                    }
                )
            }
        }
    }
}

@Composable
fun HeroHeader(
    onOpenSettings: () -> Unit,
    onOpenSourceMaterial: () -> Unit,
    hasSourceMaterial: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
    ) {
        // Generated Hero Art as Backdrop
        Image(
            painter = painterResource(id = R.drawable.lakshya_hero_art),
            contentDescription = "Lakshya AI Hero Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark gradient overlay for text legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.5f),
                            LakshyaNavyPrimary.copy(alpha = 0.85f),
                            LakshyaNavyPrimary
                        )
                    )
                )
        )

        // Header Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = LakshyaTopperGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "SEBA Class 10 Top 0.1%",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = onOpenSourceMaterial,
                        modifier = Modifier
                            .background(
                                if (hasSourceMaterial) LakshyaGoldAccent else Color.White.copy(alpha = 0.2f),
                                CircleShape
                            )
                            .testTag("source_material_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.UploadFile,
                            contentDescription = "Source Material Single Source of Truth",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .testTag("settings_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                }
            }

            Column {
                Text(
                    text = "লক্ষ্য AI (Lakshya AI)",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "অসম মাধ্যমিক শিক্ষা পৰিষদ (SEBA)ৰ বাবে বিশেষ পৰীক্ষা ইঞ্জিন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFEF08A),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Zero Hallucination • Assamese + English Bilingual • 60/40 Spaced Repetition",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun TargetMetricCard(
    totalTests: Int,
    unresolvedCount: Int,
    weakTopicsCount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("target_metric_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MetricColumn(
                value = "$totalTests",
                label = "অনুশীলন সত্ৰ (Tests)",
                color = LakshyaNavyPrimary
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(36.dp)
                    .background(Color(0xFFCBD5E1))
            )
            MetricColumn(
                value = "$unresolvedCount",
                label = "ভুল পৰ্যালোচনা (Errors)",
                color = if (unresolvedCount > 0) LakshyaAssamRed else LakshyaGreenSuccess
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(36.dp)
                    .background(Color(0xFFCBD5E1))
            )
            MetricColumn(
                value = if (weakTopicsCount > 0) "$weakTopicsCount" else "0",
                label = "লক্ষ্য বিষয় (Weak Areas)",
                color = LakshyaGoldAccent
            )
        }
    }
}

@Composable
fun MetricColumn(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = color,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}

@Composable
fun ModeLauncherGrid(
    onQuizClick: () -> Unit,
    onMockTestClick: () -> Unit,
    onMockPaperClick: () -> Unit,
    onEvaluateClick: () -> Unit,
    onMistakesClick: () -> Unit,
    onGrammarClick: () -> Unit,
    onVideoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ModeCard(
                title = "প্ৰেকটিচ কুইজ",
                subtitle = "60/40 Spaced Repetition",
                badge = "5-20 Questions",
                icon = Icons.Default.Quiz,
                color = Color(0xFF1E3A8A),
                modifier = Modifier.weight(1f),
                onClick = onQuizClick,
                testTag = "mode_quiz_card"
            )
            ModeCard(
                title = "টাইমড মক টেষ্ট",
                subtitle = "Chapter Timed 30-50M",
                badge = "Exam Pressure",
                icon = Icons.Default.Timer,
                color = Color(0xFF0F766E),
                modifier = Modifier.weight(1f),
                onClick = onMockTestClick,
                testTag = "mode_mock_test_card"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ModeCard(
                title = "SEBA মক পেপাৰ",
                subtitle = "Group A, B, C & OR",
                badge = "Full 90M Paper",
                icon = Icons.Default.Assignment,
                color = Color(0xFFB45309),
                modifier = Modifier.weight(1f),
                onClick = onMockPaperClick,
                testTag = "mode_mock_paper_card"
            )
            ModeCard(
                title = "উত্তৰ মূল্যাংকন",
                subtitle = "SEBA Rubric Examiner",
                badge = "Detailed Marks",
                icon = Icons.Default.EditNote,
                color = Color(0xFF6B21A8),
                modifier = Modifier.weight(1f),
                onClick = onEvaluateClick,
                testTag = "mode_evaluate_card"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ModeCard(
                title = "ভুল বিশ্লেষণ",
                subtitle = "Mistake Pattern Loop",
                badge = "Diagnostic",
                icon = Icons.Default.Psychology,
                color = Color(0xFFBE123C),
                modifier = Modifier.weight(1f),
                onClick = onMistakesClick,
                testTag = "mode_mistakes_card"
            )
            ModeCard(
                title = "ইংৰাজী ব্যাকৰণ",
                subtitle = "Tenses, Voice, Narration",
                badge = "Grammar Lab",
                icon = Icons.Default.MenuBook,
                color = Color(0xFF0369A1),
                modifier = Modifier.weight(1f),
                onClick = onGrammarClick,
                testTag = "mode_grammar_card"
            )
        }

        // Full width video transcript quiz
        ElevatedCard(
            onClick = onVideoClick,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("mode_video_card"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(0xFF1E293B)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(LakshyaAssamRed, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Column {
                        Text(
                            text = "YouTube ভিডিঅ' আধাৰিত কুইজ (Video Quiz)",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ভিডিঅ' ট্ৰান্সক্ৰিপ্টৰ ওপৰত নিৰ্ভৰ কৰি প্ৰশ্ন প্ৰস্তুত কৰক (Zero Guesswork)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModeCard(
    title: String,
    subtitle: String,
    badge: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        onClick = onClick,
        modifier = modifier.testTag(testTag),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color.copy(alpha = 0.12f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    color = color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = badge,
                        color = color,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun ChapterItemCard(
    subjectTitle: String,
    chapterEnglish: String,
    chapterAssamese: String,
    onStartQuiz: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onStartQuiz() }
            .testTag("chapter_${chapterEnglish.replace(" ", "_")}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chapterAssamese,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = chapterEnglish,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            FilledTonalButton(
                onClick = onStartQuiz,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = LakshyaNavyPrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "কুইজ আৰম্ভ",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun WeakTopicsRadarCard(
    weakTopics: List<com.example.data.local.WeakTopicEntity>,
    onReviseTopic: (com.example.data.local.WeakTopicEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("weak_topics_radar"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LakshyaGoldContainer.copy(alpha = 0.5f)
        ),
        border = BorderStroke(1.dp, LakshyaGoldAccent.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = LakshyaGoldAccent
                )
                Column {
                    Text(
                        text = "স্পেচড ৰিপিটিশ্বন ৰাডাৰ (Weak Topic Focus)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F)
                    )
                    Text(
                        text = "তলৰ ধাৰণাবোৰত ভুল হৈছে — লক্ষ্য AI-য়ে এইবোৰত ৬০% অধিক গুৰুত্ব দিছে",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF92400E),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            weakTopics.forEach { topic ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = topic.topic,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF451A03)
                        )
                        Text(
                            text = "${topic.subject} • ভুল: ${topic.mistakeCount} বাৰ",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF92400E),
                            fontSize = 10.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { onReviseTopic(topic) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, LakshyaGoldAccent),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF78350F)
                        )
                    ) {
                        Text("পুনৰভ্যাস", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
