package com.veberod.happyapp.feature_smilies.presentation.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.UserState
import com.veberod.happyapp.database.domain.repository.MoodRepository

@Composable
fun MoodCard(
    moodValue: Int,
    moodImage: Painter,
    userState: MutableState<UserState>,
    moodRepository: MoodRepository,
    context: Context,
    modifier: Modifier
) {
    val showPopup = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .clip(CircleShape)
            .size(140.dp)
            .clickable(onClick = {
                showPopup.value = true
            }),
        elevation = 4.dp
    ) {
        Image(painter = moodImage, contentDescription = "Smiley", contentScale = ContentScale.Fit)
    }

    if (showPopup.value /*&& userState.value.user != null*/) {
        CommentPopup(onComment = { enteredComment ->
            // insert entered comment into database
        }, onDismiss = {
            showPopup.value = false
        })
    }
}