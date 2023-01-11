package com.veberod.happyapp.feature_statistics.presentation

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veberod.happyapp.UserState
import com.veberod.happyapp.database.domain.model.Mood
import com.veberod.happyapp.database.domain.repository.MoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun Statistics(moodRepository: MoodRepository, userState: MutableState<UserState>) {
    // Add a state variable to track the time period
    val timePeriodState = remember { mutableStateOf("last 24 hours") }

    val moodEntries = runBlocking {
        userState.value.user?.let {
            withContext(Dispatchers.IO) {
                moodRepository.getMoodsById(it.userId)
            }
        }
    }
    val yStep = 1

    Column(modifier = Modifier.fillMaxSize()) {
        TextW()
        if (moodEntries != null) {
            Graph(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
                xValues = getXValuesForTimePeriod(timePeriodState.value, moodEntries),
                yValues = (1..5).map { (it) },
                points = moodEntries.map { it.mood.toFloat() },
                paddingSpace = 16.dp,
                verticalStep = yStep
            )
        }
        moodEntries?.let {
            ButtonMenu(onTimePeriodChanged = { timePeriod ->
                // Update the timePeriodState when the time period selection is changed
                timePeriodState.value = timePeriod
            })
        }
    }
}




fun getXValuesForTimePeriod(timePeriod: String, moodEntries: List<Mood>): List<Int> {
    // Print the length of the moodEntries list before filtering
    println("Number of mood entries before filtering: ${moodEntries.size}")

    val filteredMoodEntries = when (timePeriod) {
        "last 24 hours" -> {
            // Filter mood entries from the last 24 hours
            val currentTime = Calendar.getInstance().timeInMillis
            moodEntries.filter { it.date >= currentTime - TimeUnit.HOURS.toMillis(24) }
        }
        "yesterday" -> {
            // Filter mood entries from yesterday
            val yesterday = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.timeInMillis
            val today = Calendar.getInstance().timeInMillis
            moodEntries.filter { it.date in yesterday until today }
        }
        "last week" -> {
            // Filter mood entries from the last week
            val currentTime = Calendar.getInstance().timeInMillis
            moodEntries.filter { it.date >= currentTime - TimeUnit.DAYS.toMillis(7) }
        }
        "last month" -> {
            // Filter mood entries from the last month
            val currentTime = Calendar.getInstance().timeInMillis
            moodEntries.filter { it.date >= currentTime - TimeUnit.DAYS.toMillis(30) }
        }
        "last 6 months" -> {
            // Filter mood entries from the last 6 months
            val currentTime = Calendar.getInstance().timeInMillis
            moodEntries.filter { it.date >= currentTime - TimeUnit.DAYS.toMillis(180) }
        }
        else -> moodEntries
    }

    // Print the length of the filteredMoodEntries list after filtering
    println("Number of mood entries after filtering: ${filteredMoodEntries.size}")

    return filteredMoodEntries.map { it.date.toInt() }
}



@Composable
fun TextW() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = "Happiness Graph",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp),
            fontSize = 30.sp
        )
    }
}

@Composable
fun ButtonMenu(
    onTimePeriodChanged: (timePeriod: String) -> Unit,
) {
    var timePeriod: String

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Button(
            onClick = {
                timePeriod = "last 24 hours"
                onTimePeriodChanged(timePeriod)
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Today",
                style = TextStyle(fontSize = 15.sp)
            )
        }
        Button(
            onClick = {
                timePeriod = "yesterday"
                onTimePeriodChanged(timePeriod)
            }
        ) {
            Text(
                text = "Yesterday",
                style = TextStyle(fontSize = 15.sp)
            )
        }
        Button(
            onClick = {
                timePeriod = "last week"
                onTimePeriodChanged(timePeriod)
            }
        ) {
            Text(
                text = "Week",
                style = TextStyle(fontSize = 15.sp)
            )
        }
        Button(
            onClick = {
                timePeriod = "last month"
                onTimePeriodChanged(timePeriod)
            }
        ) {

            Text(
                text = "Month",
                style = TextStyle(fontSize = 15.sp)
            )
        }
    }
}


@Composable
fun Graph(
    modifier: Modifier = Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int
) {
    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    println("xValues: $xValues")
    println("points: $points")

    Box(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
            val yAxisSpace = size.height / yValues.size
            /** placing x axis points */
            for (i in xValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${xValues[i]}",
                    xAxisSpace * (i + 1),
                    size.height - 30,
                    textPaint
                )
            }
            /** placing y axis points */
            for (i in yValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${yValues[i]}",
                    paddingSpace.toPx() / 2f,
                    size.height - yAxisSpace * (i + 1),
                    textPaint
                )
            }
            /** placing our x axis points */
            for (i in xValues.indices) {
                val x1 = xAxisSpace * i
                val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                coordinates.add(PointF(x1, y1))
                /** drawing circles to indicate all the points */
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(x1, y1)
                )
            }
            /** calculating the connection points */
            for (i in 1 until coordinates.size) {
                controlPoints1.add(
                    PointF(
                        (coordinates[i].x + coordinates[i - 1].x) / 2,
                        coordinates[i - 1].y
                    )
                )
                controlPoints2.add(
                    PointF(
                        (coordinates[i].x + coordinates[i - 1].x) / 2,
                        coordinates[i].y
                    )
                )
            }
            /** drawing the path */
            val stroke = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                for (i in 0 until coordinates.size - 1) {
                    cubicTo(
                        controlPoints1[i].x, controlPoints1[i].y,
                        controlPoints2[i].x, controlPoints2[i].y,
                        coordinates[i + 1].x, coordinates[i + 1].y
                    )
                }
            }

            /** filling the area under the path */
            val fillPath = android.graphics.Path(stroke.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(xAxisSpace * xValues.last(), size.height - yAxisSpace)
                    lineTo(xAxisSpace, size.height - yAxisSpace)
                    close()
                }
            drawPath(
                fillPath,
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Cyan,
                        Color.Transparent,
                    ),
                    endY = size.height - yAxisSpace
                ),
            )
            drawPath(
                stroke,
                color = Color.Black,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}





