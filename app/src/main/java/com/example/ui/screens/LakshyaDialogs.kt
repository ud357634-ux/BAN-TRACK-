package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.LakshyaGoldAccent
import com.example.ui.theme.LakshyaGoldContainer
import com.example.ui.theme.LakshyaGreenSuccess
import com.example.ui.theme.LakshyaNavyPrimary

@Composable
fun SourceMaterialDialog(
    initialMaterial: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialMaterial) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.UploadFile,
                    contentDescription = null,
                    tint = LakshyaNavyPrimary
                )
                Text(
                    text = "একমাত্ৰ সত্যৰ উৎস (Source Material)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "SEBA পাঠ্যপুথিৰ অধ্যায় বা শিক্ষকৰ নোটৰ পাঠ্য ইয়াত পেষ্ট কৰক। লক্ষ্য AI-য়ে এই পাঠ্যক একক সঁচা তথ্য (Single Source of Truth) হিচাপে ব্যৱহাৰ কৰিব।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("পাঠ্যপুথিৰ পাঠ্য / চ্যাপ্টাৰ নোটসমূহ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("source_material_input"),
                    shape = RoundedCornerShape(10.dp),
                    minLines = 6,
                    maxLines = 12
                )

                if (text.isNotBlank()) {
                    Surface(
                        color = Color(0xFFDCFCE7),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "✓ সক্ৰিয়: প্ৰতিটো প্ৰশ্ন এই পাঠ্যৰ দ্বাৰা প্ৰমাণিত হ'ব।",
                            color = LakshyaGreenSuccess,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(text)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("সংৰক্ষণ কৰক (Save)")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}

@Composable
fun SettingsDialog(
    currentApiKey: String,
    onSaveApiKey: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var apiKey by remember { mutableStateOf(currentApiKey) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = LakshyaGoldAccent
                )
                Text(
                    text = "লক্ষ্য AI বিন্যাস (Settings & Engine)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = LakshyaGoldContainer)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Zero Hallucination Protocol:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF78350F)
                        )
                        Text(
                            text = "• কেৱল SEBA/NCERT পাঠ্যক্ৰম আৰু নিৰ্ভৰযোগ্য তথ্য\n• প্ৰশ্নকাকতৰ সকলো সমাধান দফাবাৰী পৰীক্ষিত\n• অসমীয়া আৰু ইংৰাজী দ্বিভাষিক উপস্থাপনা",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF451A03),
                            fontSize = 11.sp
                        )
                    }
                }

                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("Gemini API Key (ঐচ্ছিক / Optional)") },
                    placeholder = { Text("প্ৰয়োজনীয় নহ'লে খালী ৰাখক (Default Fallback Active)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("api_key_input"),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Text(
                    text = "API Key যোগ নকৰিলেও লক্ষ্য AI-ৰ সমৃদ্ধ অফলাইন SEBA প্ৰশ্ন ভাণ্ডাৰ সম্পূৰ্ণ সক্ৰিয় থাকে।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveApiKey(apiKey)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = LakshyaNavyPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("সংৰক্ষণ কৰক")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বন্ধ কৰক")
            }
        }
    )
}
