package com.veberod.happyapp.feature_smilies.presentation.components


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.R
import com.veberod.happyapp.UserState
import com.veberod.happyapp.database.domain.model.Mood
import com.veberod.happyapp.database.domain.repository.MoodRepository
import com.veberod.happyapp.feature_settings.components.notifications.couponIncentive
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun Smileys(moodRepository: MoodRepository, userState: MutableState<UserState>, context: Context) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val moodImages = listOf(
            painterResource(R.drawable.mood_5),
            painterResource(R.drawable.mood_4),
            painterResource(R.drawable.mood_3),
            painterResource(R.drawable.mood_2),
            painterResource(R.drawable.mood_1)
        )

        for (i in moodImages.indices) {
            CreateMoodCard(4 - i + 1, moodImages[i], userState, moodRepository, context)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CreateMoodCard(
    moodValue: Int,
    moodImage: Painter,
    userState: MutableState<UserState>,
    moodRepository: MoodRepository,
    context: Context
) {
    val showPopup = remember { mutableStateOf(false) }
    val comment = remember { mutableStateOf("") }
    val locationHelper = LocationHelper(context)
    val geolocation = locationHelper.getCurrentLocation().toString()
    val currentTimeAndDate = System.currentTimeMillis()
    val showCoupon = remember { mutableStateOf(false) }
    //If there is date in database get it
    //Get days in a row from database
    val daysInARow = remember { mutableStateOf(0) }
    //var lastClickDate = remember { LocalDate.now() }

    Card(modifier = Modifier
        .clip(CircleShape)
        .size(140.dp)
        .clickable(onClick = {
            showPopup.value = true
            daysInARow.value ++
            if (daysInARow.value > 7){
                daysInARow.value = 1
            }
            showCoupon.value = true
        }), elevation = 4.dp
    ) {
        Image(painter = moodImage, contentDescription = "Smiley", contentScale = ContentScale.Fit)
    }

    if (showPopup.value /*&& userState.value.user != null*/) {
        CommentPopup { enteredComment ->
            comment.value = enteredComment
            val userId = userState.value.user!!.userId
            val commentAsString = comment.value
            showPopup.value = false

            val mood = Mood(
                0,
                userId,
                currentTimeAndDate,
                moodValue,
                commentAsString,
                geolocation
            )
            insertMood(mood, moodRepository, context)
        }
    }
    if (showCoupon.value){
        showCoupon.value = couponIncentive(daysInARow.value)
    }
}


@Composable
fun CommentPopup(onComment: (String) -> Unit) {
    val comment = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { },
        title = { Text("Comment") },
        text = { TextField(value = comment.value, onValueChange = { comment.value = it }) },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    onComment(comment.value)
                }) {
                    Text("Submit comment")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    onComment("")
                }) {
                    Text("Skip comment")
                }
            }

        }
    )
}

fun insertMood(mood: Mood, moodRepository: MoodRepository, context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            moodRepository.insert(mood)
            withContext(Dispatchers.Main) {
                // Update the UI to reflect the successful insertion
                moodRepository.showToast(context,"Mood inserted successfully")
            }
        } catch (e: Exception) {
            // Handle the error
            withContext(Dispatchers.Main) {
                // Update the UI to show the error message
                moodRepository.showToast(context,"Error: ${e.message}")
            }
        }
    }
}

