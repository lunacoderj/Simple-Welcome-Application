package com.mad.app.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mad.app.ui.components.GlassCard
import com.mad.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var courseInterest by remember { mutableStateOf("") }
    var submittedData by remember { mutableStateOf<ContactData?>(null) }
    
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInHorizontally()
    ) {
        Box(modifier = Modifier.fillMaxSize().background(DeepBlue)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(60.dp))
                    // ANITS Logo Placeholder
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White, RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("ANITS", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Anil Neerukonda Institute of Technology and Sciences",
                        style = MaterialTheme.typography.titleMedium,
                        color = AI_Cyan,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "A premier institution for engineering excellence and innovation.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Contact Form",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = TextPrimary),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = AI_Cyan,
                                unfocusedBorderColor = GlassBorder,
                                focusedLabelColor = AI_Cyan,
                                unfocusedLabelColor = TextSecondary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email Address") },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = TextPrimary),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = AI_Cyan,
                                unfocusedBorderColor = GlassBorder,
                                focusedLabelColor = AI_Cyan,
                                unfocusedLabelColor = TextSecondary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedTextField(
                            value = courseInterest,
                            onValueChange = { courseInterest = it },
                            label = { Text("Course of Interest") },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(color = TextPrimary),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = AI_Cyan,
                                unfocusedBorderColor = GlassBorder,
                                focusedLabelColor = AI_Cyan,
                                unfocusedLabelColor = TextSecondary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = {
                                if (name.isNotEmpty() && email.isNotEmpty()) {
                                    submittedData = ContactData(name, email, courseInterest)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = AI_Cyan),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Submit Inquiry", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                item {
                    submittedData?.let { data ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 100.dp),
                            colors = CardDefaults.elevatedCardColors(containerColor = AI_Purple),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    text = "Submission Received! ✨",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Name: ${data.name}", color = Color.White)
                                Text("Email: ${data.email}", color = Color.White)
                                Text("Course: ${data.courseInterest}", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class ContactData(val name: String, val email: String, val courseInterest: String)
