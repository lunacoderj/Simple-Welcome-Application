package com.mad.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mad.app.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(GlassBackground)
            .border(1.dp, GlassBorder, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun CourseCard(
    title: String,
    description: String,
    imageUrl: String,
    onLearnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(AI_Purple.copy(alpha = 0.1f)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            maxLines = 2,
            minLines = 2
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onLearnClick,
            colors = ButtonDefaults.buttonColors(containerColor = AI_Cyan),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("Learn", color = Color.Black, fontSize = 14.sp)
        }
    }
}

@Composable
fun FeaturedCoursesRow(
    courses: List<Triple<String, String, String>>, 
    onMoreClick: () -> Unit, 
    onLearnClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Featured Courses",
                style = MaterialTheme.typography.headlineSmall,
                color = AI_Cyan,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = onMoreClick) {
                Text("View All", color = AI_Purple)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            courses.take(2).forEach { (title, desc, url) ->
                CourseCard(
                    title = title,
                    description = desc,
                    imageUrl = url,
                    onLearnClick = { onLearnClick(title) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CTABanner(
    title: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(AI_Cyan.copy(alpha = 0.8f), AI_Purple.copy(alpha = 0.8f))
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                onClick = onClick,
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(buttonText, color = Color.Black)
            }
        }
    }
}
