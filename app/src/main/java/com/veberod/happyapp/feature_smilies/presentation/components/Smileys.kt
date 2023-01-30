package com.veberod.happyapp.feature_smilies.presentation.components


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.R
import com.veberod.happyapp.UserState
import com.veberod.happyapp.database.domain.repository.MoodRepository

@Composable
fun Smileys(moodRepository: MoodRepository, userState: MutableState<UserState>, context: Context) {
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 60.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val moodImages = listOf(
            painterResource(R.drawable.mood_5),
            painterResource(R.drawable.mood_4),
            painterResource(R.drawable.mood_3),
            painterResource(R.drawable.mood_2),
            painterResource(R.drawable.mood_1)
        )

        for (i in moodImages.indices) {
            MoodCard(4 - i + 1, moodImages[i], userState, moodRepository, context, Modifier.weight(1f))
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

